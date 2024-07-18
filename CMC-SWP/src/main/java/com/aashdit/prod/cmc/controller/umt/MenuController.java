package com.aashdit.prod.cmc.controller.umt;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.service.umt.MenuService;
import com.aashdit.prod.cmc.service.umt.RoleService;


@Controller
@RequestMapping(value = "/admin/menu")
public class MenuController {
	
	final static Logger logger = Logger.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	
	
	
	@RequestMapping(value="/{role}", produces={"application/JSON"}, name="Get Menus by Role", method=RequestMethod.GET)
	@ResponseBody
	public String getAllPublishedMenusForReorder(@PathVariable("role")String roleCode)
	{
		ServiceOutcome<Role> svcOutcome = roleService.getRoleByCode(roleCode);
		Role role = svcOutcome.getData();
		
		JSONArray ja =  menuService.getAllMenus(true, role);
		
		return ja.toString();
	}
	
	@RequestMapping(value="/assign", produces={"application/JSON"}, name="Assign Menu to Role", method=RequestMethod.POST)
	@ResponseBody
	public Boolean assignMenu(@RequestParam("data[]")Long[] menuIds, @RequestParam("roleCode")String roleCode)
	{
		Boolean retVal = false;
		//System.out.println("assignMenu======================");
		try{
			retVal = menuService.assignMenu(menuIds, roleCode);
			
			/*if(retVal){
				
			}*/
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		return retVal;
	}
	
	@GetMapping(path="/reorder", produces = MediaType.APPLICATION_JSON_VALUE, name="Arrange Menu Data URL")
	@ResponseBody
	private String getMenuForReorder()
	{
		return menuService.getMenusForReorder().toString();
	}
	
	@GetMapping(path="/arrange", name="Arrange Menu")
	private String arrangeMenu(Model model)
	{
		return "admin.menu.reorder";
	}
	
	@RequestMapping(value="/saveOrder", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public String saveMenOrder(Model model, @RequestParam Long menuId, @RequestParam Long parentMenuId, @RequestParam Integer order)
	{
		Boolean outcome = menuService.saveMenuOrder(menuId, parentMenuId, order);
		
		return String.valueOf(outcome);
	}
	
	@RequestMapping(value="/assinged/{role}", produces={"application/JSON"}, name="Get Menus by Role", method=RequestMethod.GET)
	@ResponseBody
	public String getAllAssingedMenu(@PathVariable("role")String roleCode)
	{
		ServiceOutcome<Role> svcOutcome = roleService.getRoleByCode(roleCode);
		Role role = svcOutcome.getData();
		
		JSONArray ja =  menuService.getAllAssingedMenusForRole(role);
		
		return ja.toString();
	}
}
