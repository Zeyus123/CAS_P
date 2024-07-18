package com.aashdit.prod.cmc.service.umt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleMenuMap;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.MenuRepository;
import com.aashdit.prod.cmc.repository.umt.PriviledgeRepository;
import com.aashdit.prod.cmc.repository.umt.RoleMenuMapRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRepository;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Service
public class MenuServiceImpl implements MenuService, MessageSourceAware{

	final static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private MenuRepository menuRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RoleMenuMapRepository roleMenuMapRepository;
	
	@Autowired
	private PriviledgeRepository priviledgeRepository;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Autowired
	private RequestMappingHandlerMapping handlerMapping;
	
	@Override
	@Transactional
	public JSONArray getAllMenus(Boolean isActive, Role role) {
		
		User user = SecurityHelper.getCurrentUser();
		//Get All top Level Menus
		List<Menu> menus = menuRepository.findByIsActive(isActive);
		menus = menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());
		if (!user.getPrimaryRole().getRoleCode().equals("ROLE_SYSTEM"))
		{
			menus = menus.stream().filter(m -> !(m.getMenuURL() != null && m.getMenuURL().startsWith("/system"))).collect(Collectors.toList());
		}
		menus = menus.stream().sorted((a,b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder())).collect(Collectors.toList());
		
		JSONArray ja = new JSONArray();
		
		try {
			ja = prepareMyChildMenus(menus, role, false);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ja;
	}
	
	private JSONArray prepareMyChildMenus(List<Menu> menus, Role role, Boolean includePrivilege)
	{
		JSONArray ja = new JSONArray();
		menus = menus.stream().sorted((a,b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder())).collect(Collectors.toList());
		
		try {
			
			List<Menu> lstAllocatedMenus = new ArrayList<>();
			if (role != null)
			{
				lstAllocatedMenus = getMenusAssingedToRole(role.getRoleId());
			}
			else
			{
				lstAllocatedMenus = menuRepository.findByIsActive(true);
			}
					
			
			for (Menu menu : menus)
			{
				if (menu.getIsActive())
				{
					JSONObject jobj = new JSONObject();
					jobj.put("title", menu.getMenuText());
					jobj.put("expanded",false);
					jobj.put("folder",menu.getIsParent());
					jobj.put("id", menu.getMenuId());
					jobj.put("url", menu.getMenuURL());
					jobj.put("parentCode", menu.getParent() == null ? 0 : menu.getParent().getMenuId());
					jobj.put("display", menu.getIsDisplay());
					jobj.put("isParent", menu.getIsParent());
					jobj.put("appCode", menu.getAppCode());
					if (role != null)
					{
						Optional<Menu> allocated = lstAllocatedMenus.stream().filter(m -> m.getMenuId() == menu.getMenuId()).findFirst();
						if (allocated.isPresent())
						{
							jobj.put("selected", true);
						}
						else
						{
							jobj.put("selected", false);
						}
					}
					
					if (includePrivilege == true && role != null)
					{
						List<Privilege> lstPriviledge = priviledgeRepository.findByMenuAndRole(menu.getMenuId(), role.getRoleId());
						JSONArray jaPrivs = new JSONArray();
						for (Privilege prv : lstPriviledge)
						{
							JSONObject joPriv =new JSONObject();
							joPriv.put("id", prv.getId());
							joPriv.put("desc", prv.getDesc());
							jaPrivs.put(joPriv);
						}
						jobj.put("privs",jaPrivs);
						
					}

					Hibernate.initialize(menu.getChildren());
					List<Menu> children = menu.getChildren().stream().collect(Collectors.toList());
					children = children.stream().sorted((a,b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder())).collect(Collectors.toList());
					
					jobj.put("children", prepareMyChildMenus(children, role, includePrivilege));
				
					ja.put(jobj);
				}
				
			}
			
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ja;
		
		
	}

	@Override
	@Transactional
	@Modifying
	public Boolean assignMenu(Long[] menuIds, String roleCode) {
		Boolean outcome = true;
		
		try
		{
			Role dbRole = roleRepository.findByRoleCode(roleCode);

			List<RoleMenuMap> currentMenuMaps = roleMenuMapRepository.findByRoleId(dbRole.getRoleId());
			
			for(Long menuId : menuIds)
			{
			
				Optional<RoleMenuMap> rmMap = currentMenuMaps.stream().filter(m -> m.getMenuId().equals(menuId)).findFirst(); 
			    if (!rmMap.isPresent()) 
			    { 
			    	//No entry in RM Map table. Add an entry
			    	RoleMenuMap rmm = new RoleMenuMap();
			    	rmm.setRoleId(dbRole.getRoleId());
			    	rmm.setMenuId(menuId);
			    	rmm.setIsActive(true);
			    	
			    	roleMenuMapRepository.save(rmm);
			    	logger.debug("Added Menu " +  menuId + " to role : " + roleCode);
			    	
			    	outcome = outcome & true;
	
			    }
			    else
			    {
			    	//Entry is present in RM Map table. If the menu has come from front end it is hecked, so make it active
			    	RoleMenuMap rmm = rmMap.get();
			    	rmm.setIsActive(true);
			    	roleMenuMapRepository.save(rmm);
			    	logger.debug(" Menu " + menuId + "was " + !rmm.getIsActive() + " from role : " + roleCode);
			    	
			    	outcome = outcome & true;
			    }
			    
			}
			
			//Inactivation code
			for (RoleMenuMap rmm2 : currentMenuMaps)
			{
				List<Long> activeUIMenuIds = Arrays.asList(menuIds);
				if (!activeUIMenuIds.contains(rmm2.getMenuId()))
				{
					rmm2.setIsActive(false);

			    	roleMenuMapRepository.save(rmm2);
			    	logger.debug("Remobed Menu " +  rmm2.getMenuId() + " from role : " + roleCode);
			    	outcome = outcome & true;
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
			outcome = false;
		}
		
		return outcome;
		
	}

	@Override
	public List<Menu> getUIMenus(Role role) {
		List<Menu> menus = new ArrayList<Menu>();
		
		try
		{
			menus = menuRepository.findMenuByRoleId(role.getRoleId());

		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		
		return menus;
	}

	@Override
	public List<Privilege> getPriviledgesForRoleAndMenu(Long roleId, Long menuId) {
		List<Privilege> lstPriviledges = new ArrayList<Privilege>();
		try
		{
			StringBuilder query = new StringBuilder();
			query.append("select p.*  ");
			query.append("from t_umt_role_menu_privilege_map rpm ");
			query.append("join t_umt_role_menu_map rmm ");
			query.append("on rmm.role_menu_map_id = rpm.role_menu_id ");
			query.append("join t_umt_mst_privilege p ");
			query.append("on p.mst_privilege_id = rpm.privilege_id ");
			query.append("where rmm.role_id = :roleId ");
			query.append("and rmm.menu_id = :menuId ");
			
			Query qry = entityManager.createNativeQuery(query.toString(), Privilege.class);
			qry.setParameter("roleId", roleId);
			qry.setParameter("menuId", menuId);
			
			lstPriviledges = qry.getResultList();

		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage());
		}
		
		return lstPriviledges;
	}


	@Override
	public ServiceOutcome<Menu> saveModule(Menu menu) {
		ServiceOutcome<Menu> svcOutcome = new ServiceOutcome<Menu>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			
			if (menu.getMenuId() == null) //INSERT
			{
				menu.setIsDisplay(true);
				menu.setIsModule(true);
				menu.setIsSystemConfigEntry(false);
				menu.setParent(null);
				
				menu.setIsActive(menu.getIsActive() == null ? false : true);
				
				menu.setCreatedBy(user);
				menu.setCreatedOn(new Date());
				
				menu = menuRepository.save(menu);
				svcOutcome.setData(menu);
			}
			else //UPDATE
			{
				Menu dbMenu = menuRepository.getOne(menu.getMenuId());
				dbMenu.setMenuTextEN(menu.getMenuTextEN());
				dbMenu.setMenuTextHI(menu.getMenuTextHI());
				dbMenu.setMenuIcon(menu.getMenuIcon());
				dbMenu.setIsActive(menu.getIsActive() == null ? false : true);
				dbMenu.setDisplayOrder(menu.getDisplayOrder());
				
				dbMenu.setLastUpdatedBy(user);
				dbMenu.setLastUpdatedOn(new Date());
				
				menu = menuRepository.save(dbMenu);
				svcOutcome.setData(dbMenu);
			}
						
			svcOutcome.setMessage(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));

		}catch(Exception ex)
		{
			logger.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<List<Menu>> getModuleList() {
		ServiceOutcome<List<Menu>> svcOutcome = new ServiceOutcome<>();
		try
		{
			List<Menu> lstMenus =  menuRepository.findModuleMenus();
			svcOutcome.setData(lstMenus);
		}
		catch(Exception ex)
		{
			logger.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<Menu> getModule(Long menuId) {
		ServiceOutcome<Menu> svcOutcome = new ServiceOutcome<>();
		try
		{
			Menu menu =  menuRepository.getOne(menuId);
			svcOutcome.setData(menu);
		}
		catch(Exception ex)
		{
			logger.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}

	@Override
	public ServiceOutcome<List<Menu>> getAppMenuList(Boolean init) {
		ServiceOutcome<List<Menu>> svcOutcome = new ServiceOutcome<>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			List<Menu> lstMenus =  menuRepository.findAppMenus();
			
			if (init == true)
			{
				Set<RequestMappingInfo> endPoints =  handlerMapping.getHandlerMethods().keySet();
				for (RequestMappingInfo rmi : endPoints)
				{
					String urlPattern = (String) rmi.getPatternsCondition().getPatterns().toArray()[0];
					Optional<Menu> dbEntry = lstMenus.stream()
											.filter(m -> m.getMenuURL().equals(urlPattern))
											.findFirst();
					if (!dbEntry.isPresent())
					{
						String defaultMenuText = rmi.getName() == null ? "Not Set" : rmi.getName();
						Menu mnuNew = new Menu();
						mnuNew.setMenuURL(urlPattern);
						mnuNew.setCreatedBy(user);
						mnuNew.setCreatedOn(new Date());
						mnuNew.setMenuTextEN(defaultMenuText);
						mnuNew.setMenuTextHI(defaultMenuText);
						
						mnuNew.setIsActive(true);
	
						Boolean isDisplay = determineDisplay(rmi);
						mnuNew.setIsDisplay(isDisplay);
						
						mnuNew.setIsModule(false);
						mnuNew.setIsSystemConfigEntry(false);
						mnuNew.setParent(null);
						mnuNew.setDisplayOrder(99);
						
						mnuNew = menuRepository.save(mnuNew);
					}
					else
					{
						Menu dbMenu = menuRepository.getOne(dbEntry.get().getMenuId());
						Boolean isDisplay = determineDisplay(rmi);
						dbMenu.setIsDisplay(isDisplay);
						menuRepository.save(dbMenu);
						
					}
				}
				//Requery after save
				lstMenus =  menuRepository.findAppMenus();
			}
			svcOutcome.setData(lstMenus);
		}
		catch(Exception ex)
		{
			logger.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}
	
	private Boolean determineDisplay(RequestMappingInfo rmi)
	{
		Boolean isDisplay = true;
		try
		{
			Set<RequestMethod> rms = rmi.getMethodsCondition().getMethods();
			for (RequestMethod rm : rms)
			{
				if (rm.name().equals(RequestMethod.GET.toString()))
				{
					//This request has both GET and maybe POST. So it should be displayed
					isDisplay = true;
					
					//Does this produce anything else than "text/html"? if so dont display as it maybe AJAX Call
					Set<MediaType> mts = rmi.getProducesCondition().getProducibleMediaTypes();
					for(MediaType mt : mts)
					{
						if (!mt.equals(MediaType.TEXT_HTML))
						{
							isDisplay = false;
							break;
						}
					}
					
					break;
				}
				else //NON GET URL should not be set to display
				{
					isDisplay = false;
				}
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		
		return isDisplay;
	}

	@Override
	public ServiceOutcome<Menu> saveMenu(Menu menu) {
		ServiceOutcome<Menu> svcOutcome = new ServiceOutcome<Menu>();
		try
		{
			User user = SecurityHelper.getCurrentUser();
			
			if (menu.getMenuId() == null) //INSERT
			{
				menu.setIsModule(false);
				menu.setIsSystemConfigEntry(false);
				
				menu.setIsActive(menu.getIsActive() == null? false : true);
				menu.setIsDisplay(menu.getIsDisplay() == null? false : true);
				
				menu.setCreatedBy(user);
				menu.setCreatedOn(new Date());
				
				menu = menuRepository.save(menu);
				svcOutcome.setData(menu);
			}
			else //UPDATE
			{
				Menu dbMenu = menuRepository.getOne(menu.getMenuId());
				dbMenu.setMenuTextEN(menu.getMenuTextEN());
				dbMenu.setMenuTextHI(menu.getMenuTextHI());
				dbMenu.setMenuIcon(menu.getMenuIcon());
				dbMenu.setIsActive(menu.getIsActive() == null? false : true);
				dbMenu.setIsDisplay(menu.getIsDisplay() == null? false : true);
				dbMenu.setDisplayOrder(menu.getDisplayOrder());
				dbMenu.setMenuURL(menu.getMenuURL());
				
				dbMenu.setLastUpdatedBy(user);
				dbMenu.setLastUpdatedOn(new Date());
				
				menu = menuRepository.save(dbMenu);
				svcOutcome.setData(dbMenu);
			}
						
			svcOutcome.setMessage(messageSource.getMessage("msg.success",null, LocaleContextHolder.getLocale()));

		}catch(Exception ex)
		{
			logger.error(ex);
			
			svcOutcome.setData(null);
			svcOutcome.setOutcome(false);
			svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return svcOutcome;
	}
	
	private List<Menu> getMenusAssingedToRole(Long roleId)
	{
		List<Menu> lstMenu = new ArrayList<>();
		
		StringBuilder querySQL = new StringBuilder();
		querySQL.append("select m.* ");
		querySQL.append("from t_umt_menu m ");
		querySQL.append("join t_umt_role_menu_map rmm  ");
		querySQL.append("	on rmm.menu_id = m.menu_id ");
		querySQL.append("	and rmm.role_id = :roleId  ");
		querySQL.append("	and rmm.isactive = true  ");
		querySQL.append(" where m.is_active = true ");
		
		Query query = entityManager.createNativeQuery(querySQL.toString(), Menu.class);
		query.setParameter("roleId", roleId);
		
		lstMenu = query.getResultList();

		return lstMenu;
	}

	@Override
	public JSONArray getMenusForReorder() {
		//Get All top Level Menus
		List<Menu> menus = menuRepository.findByIsActive(true);
		menus = menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());
		menus = menus.stream().filter(m -> !(m.getMenuURL() != null && m.getMenuURL().startsWith("/system"))).collect(Collectors.toList());
		menus = menus.stream().sorted((a,b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder())).collect(Collectors.toList());
		
		JSONArray ja = new JSONArray();
		
		try {
			ja = prepareMyChildMenus(menus, null, false);
			
		} catch (Exception ex) {
			logger.error(ex);
		}
		
		return ja;
	}

	@Override
	public Boolean saveMenuOrder(Long menuId, Long parentMenuId, Integer order) {
		Boolean outCome = false;
		try
		{
			Menu parentMenu = menuRepository.getOne(parentMenuId);
			Menu thisMenu = menuRepository.getOne(menuId);
			thisMenu.setParent(parentMenu);
			thisMenu.setDisplayOrder(order);
			
			menuRepository.save(thisMenu);
			outCome = true;
		}
		catch(Exception ex)
		{
			logger.error(ex);
		}
		
		return outCome;
	}

	@Override
	public JSONArray getAllAssingedMenusForRole(Role role) {
		User user = SecurityHelper.getCurrentUser();
		//Get All top Level Menus
		List<Menu> menus = menuRepository.findMenuByRoleId(role.getRoleId());
		menus = menus.stream().filter(m -> m.getIsDisplay() == true).collect(Collectors.toList());
		menus = menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());
		if (!user.getPrimaryRole().getRoleCode().equals("ROLE_SYSTEM"))
		{
			menus = menus.stream().filter(m -> !(m.getMenuURL() != null && m.getMenuURL().startsWith("/system"))).collect(Collectors.toList());
		}
		menus = menus.stream().sorted((a,b) -> a.getDisplayOrder().compareTo(b.getDisplayOrder())).collect(Collectors.toList());
		
		JSONArray ja = new JSONArray();
		
		try {
			ja = prepareMyChildMenus(menus, role, true);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ja;
	}

	@Override
	public List<Menu> getAllMenus() {
		return menuRepository.findAll();
	}

	@Override
	public ServiceOutcome<List<Menu>> getMenuPagedList() {
		ServiceOutcome<List<Menu>> serviceOutcome=new ServiceOutcome<List<Menu>>();
		try {
			List<Menu> userList=menuRepository.findNonModuleNonSystemEntries();
			serviceOutcome.setData(userList);
		} catch (Exception e) {
              logger.error(e);
              serviceOutcome.setData(null);
              serviceOutcome.setOutcome(false);
              serviceOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return serviceOutcome;
	}

	@Override
	public ServiceOutcome<Menu> getMenuById(Long menuId) {
		ServiceOutcome<Menu> svcOutcome = new  ServiceOutcome<Menu>();
		 
		 try
		 {
			 Menu menu = menuRepository.getOne(menuId);
			 svcOutcome.setData(menu);
		 }
		 catch(Exception ex)
		 {
			 logger.error(ex);
			 svcOutcome.setData(null);
			 svcOutcome.setOutcome(false);
			 svcOutcome.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		 }
		 
		 return svcOutcome;
	}

	@Override
	public List<Menu> findUIMenusByRole(Long roleId) {
		List<Menu> menuList=menuRepository.findUIMenusByRole(roleId);
		return menuList;
	}

}
