package com.aashdit.prod.cmc.service.umt;

import java.util.List;

import org.json.JSONArray;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.Role;


public interface MenuService {

	JSONArray getAllMenus(Boolean isActive, Role role);
	
	Boolean  assignMenu(Long[] menuIds, String role);
	
	List<Menu> getUIMenus(Role role);
	
	List<Privilege> getPriviledgesForRoleAndMenu(Long roleId, Long menuId);
	
	List<Menu> getAllMenus();


	ServiceOutcome<Menu> saveModule(Menu menu);
	
	ServiceOutcome<List<Menu>> getModuleList();
	
	ServiceOutcome<Menu> getModule(Long menuId);
	
	ServiceOutcome<List<Menu>> getAppMenuList(Boolean init);
	
	ServiceOutcome<Menu> saveMenu(Menu menu);
	
	JSONArray getMenusForReorder();
	
	Boolean saveMenuOrder(Long menuId,Long parentMenuId, Integer order);
	
	JSONArray getAllAssingedMenusForRole(Role role);
	
	/* API Sepecific Calls */
	ServiceOutcome<List<Menu>> getMenuPagedList();
	
	ServiceOutcome<Menu> getMenuById(Long menuId);

	List<Menu> findUIMenusByRole(Long roleId);
	
		
}
