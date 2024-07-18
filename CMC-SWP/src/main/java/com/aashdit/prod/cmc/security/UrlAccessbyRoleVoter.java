package com.aashdit.prod.cmc.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.LoggedInUser;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.service.umt.MenuService;
import com.aashdit.prod.cmc.service.umt.UserService;

public class UrlAccessbyRoleVoter implements AccessDecisionVoter<Object> {

	final static Logger logger = Logger.getLogger(UrlAccessbyRoleVoter.class);

	@Autowired
	private MenuService menuService;

	List<Menu> m_menus;

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {

		int whatISay = AccessDecisionVoter.ACCESS_ABSTAIN;

		if (authentication.getPrincipal().toString().equals("anonymousUser")) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		FilterInvocation fi = (FilterInvocation) object;
		fi.getHttpRequest().getSession().setAttribute("USER_PRIVILEGES", new ArrayList<Privilege>());

		String requestURL = fi.getRequestUrl();

		PathMatcher pathMatcher = new AntPathMatcher();

		if (pathMatcher.match("/assets/**", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/loginPage/**", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/login*", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/core/api/otpSubmissionForPwd**", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/core/api/checkDuplicateUser**", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/forgotpassword*", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		if (pathMatcher.match("/api/**", requestURL)) {
			return AccessDecisionVoter.ACCESS_GRANTED;
		}

		// Allow all request to home page if user is authenticated
		try {
			LoggedInUser au = (LoggedInUser) authentication.getPrincipal();
			if (au != null) {
				if (pathMatcher.match("/", requestURL)) {
					return AccessDecisionVoter.ACCESS_GRANTED;
				}
			}
		} catch (Exception ex) {
			logger.warn("UrlAccessbyRoleVoter : Failed converting authenticated principal to LoggedinUser");
		}

		Long authId = ((LoggedInUser) authentication.getPrincipal()).getDbUser().getUserId();
		// logger.debug("Auth Id is => " + authId);
		ServiceOutcome<User> svcOutcome = userService.findByUserId(authId);
		User dbUser = svcOutcome.getData();
		if (dbUser != null) {
			if (dbUser.getIsActive() == false) {
				authentication.setAuthenticated(false);
				return AccessDecisionVoter.ACCESS_DENIED;
			}
		}

		authentication.getAuthorities();

		Role currRole = ((LoggedInUser) authentication.getPrincipal()).getPrimaryRole();
		// m_menus = menuService.getUIMenus(currRole);
		m_menus = menuService.getUIMenus(currRole);

		List<String> allowedURLS = new ArrayList<String>();
		List<Menu> finalList = new ArrayList<Menu>();

		m_menus.forEach(m -> {
			m.setChildren(null);
			List<Menu> children = getMenuChildren(m);
			m.setChildren(children);
			finalList.add(m);
		});

		finalList.forEach(m -> {
			if (m.getMenuURL() != null) {
				String binaDotURL = m.getMenuURL().replace("./", "/");
				allowedURLS.add(binaDotURL);
			}
		});

		whatISay = AccessDecisionVoter.ACCESS_DENIED;

		for (String url : allowedURLS) {
			// logger.debug(requestURL + " :: " + url);
			if (pathMatcher.match(url, requestURL)) {
				whatISay = AccessDecisionVoter.ACCESS_GRANTED;

				Optional<Menu> matchedMenu = m_menus.stream()
						.filter(m -> m.getMenuURL() != null && m.getMenuURL().replace("./", "/").equals(requestURL))
						.findFirst();
				if (matchedMenu.isPresent()) {
					Long currMenuId = matchedMenu.get().getMenuId();
					logger.debug("Current Menu Id is " + currMenuId);

					List<Privilege> privs = menuService.getPriviledgesForRoleAndMenu(currRole.getRoleId(), currMenuId);
					fi.getHttpRequest().getSession().setAttribute("USER_PRIVILEGES", privs);
				}

				break;
			}
		}

		logger.debug("UrlAccessbyRoleVoter Outcome is " + whatISay);

		return whatISay; // AccessDecisionVoter.ACCESS_GRANTED;
	}

	private List<Menu> getMenuChildren(Menu parent) {
		List<Menu> children = m_menus.stream()
				.filter(m -> (m.getParent() != null && m.getParent().getMenuId() == parent.getMenuId()))
				.collect(Collectors.toList());

		children.forEach(m -> {
			m.setChildren(null);
			List<Menu> children2 = getMenuChildren(m);
			m.setChildren(children2);

		});

		return children;
	}

}
