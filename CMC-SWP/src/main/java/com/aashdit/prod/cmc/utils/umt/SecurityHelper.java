package com.aashdit.prod.cmc.utils.umt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.aashdit.prod.cmc.VO.CurrentUserVo;
import com.aashdit.prod.cmc.model.umt.LoggedInUser;
import com.aashdit.prod.cmc.model.umt.User;

public class SecurityHelper {
	public static User getCurrentUser() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			LoggedInUser currentUser = (LoggedInUser) auth.getPrincipal();
			CurrentUserVo currUserVo = currentUser.getCurrentUserVo(); 
			User user = currentUser.getDbUser();
			user.setCurrentUserVo(currUserVo);
			return user;
		}
		catch(Exception e) {
			return null;
		}
	}
	
    public static void updateCurrentUserVo(CurrentUserVo currentUserVo) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LoggedInUser loggedInUser = (LoggedInUser) principal;
		LoggedInUser updatedLoggedInUser = new LoggedInUser(loggedInUser.getUsername(), loggedInUser.getPassword(), loggedInUser.isEnabled(), loggedInUser.isAccountNonExpired(), loggedInUser.isCredentialsNonExpired(), loggedInUser.isAccountNonLocked(), loggedInUser.getAuthorities(), currentUserVo.getPrimaryRole(), loggedInUser.getDbUser(), currentUserVo);

		// delete old authentication token
		SecurityContextHolder.clearContext();

		// create new authentication token
		Authentication newAuth = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(updatedLoggedInUser, loggedInUser.getPassword(), loggedInUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);
	}
}
	