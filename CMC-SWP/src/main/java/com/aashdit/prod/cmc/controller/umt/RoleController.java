package com.aashdit.prod.cmc.controller.umt;

import java.util.List;

import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleLevelMap;
import com.aashdit.prod.cmc.service.umt.RoleService;

@Controller
@RequestMapping(value = "/admin/role")
public class RoleController {

private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(path="/list",name="Role List")
	public String userList(Model model) {
		
		ServiceOutcome<List<Role>> outcome=roleService.getAllRoles(true);
		if(outcome.getOutcome()) {
			model.addAttribute("roleList", outcome.getData());
		}else {
			model.addAttribute("error_msg", outcome.getMessage());
		}
		
		
		return "site.admin.roleList";
		
	}

	@GetMapping(path="/edit/{id}",name="Edit Role")
	public String userEdit(Model model,@PathVariable("id")Long roleId) {
		
		ServiceOutcome<Role> outcome=roleService.findByRoleId(roleId);
		if(outcome.getOutcome()) {
			model.addAttribute("serviceOutcome", outcome);
		}else {
			model.addAttribute("error_msg", outcome.getMessage());
		}
	
		
		return "site.admin.roleEdit";
		
	}
	
	@GetMapping(path="/view/{id}",name="Role View")
	public String userView(Model model,@PathVariable("id")Long roleId) {
		
		ServiceOutcome<Role> outcome=roleService.findByRoleId(roleId);
		if(outcome.getOutcome()) {
			model.addAttribute("serviceOutcome", outcome);
		}else {
			model.addAttribute("error_msg", outcome.getMessage());
		}
	
		
		return "site.admin.roleView";
		
	}
	
	@PostMapping(path="/isActive",name="Active And InActive Role")
	public String lockNUnlock(RedirectAttributes attr,Boolean isActive,Long roleId) {
		
		ServiceOutcome<Role> serviceOutcome=roleService.lockNUnlockRoleById(roleId,isActive);
		if(serviceOutcome.getOutcome()) {
			attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
		}else {
			attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
		}
		
		
		return "redirect:/admin/role/list";
		
	}
	@PostMapping(path="/addNupdate",name="Add And Update Role")
	public String userUpdate(RedirectAttributes attr,Role role) {
		
	ServiceOutcome<Role> serviceOutcome=roleService.addNupdateRole(role);
	if(serviceOutcome.getOutcome()) {
		attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
	}else {
		attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
	}
		
		return "redirect:/admin/role/list";
	}
	
	@GetMapping(path="/roleLevelMap/{id}",name="Role Leve Map")
	public String roleLevelMap(@PathVariable("id")Long roleId,Model model) {
		
		ServiceOutcome<List<RoleLevelMap>> serviceOutcome=roleService.findRoleLevelListByRoleId(roleId);
		if(!serviceOutcome.getOutcome()) {
			model.addAttribute("error_msg", serviceOutcome.getMessage());
		}else {
			model.addAttribute("roleLevelList", roleService.roleRightLevelList().getData());
			model.addAttribute("roleData", roleService.findByRoleId(roleId).getData());
			model.addAttribute("roleMapList", serviceOutcome.getData());
		}
	
		return "site.admin.roleLevelMapList";
		
	}
	
	@PostMapping(path="/roleLevelMap/addNupdate",name="Role to Level Assignment", produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ServiceOutcome<RoleLevelMap> addNupdate(RedirectAttributes attr,@RequestParam("maxAllocations")Long[] maxAllocations,
							@RequestParam("roleLevelId")Long[] roleLevelId , @RequestParam("status")Boolean[] status, Long roleId) {
		
		ServiceOutcome<RoleLevelMap> serviceOutcome=roleService.addNupdateRoleLevelMap(maxAllocations,roleLevelId,status,roleId);
		
		/*
		if(serviceOutcome.getOutcome()) {
			attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
		}else {
			attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
			
		}
		*/
				
		return serviceOutcome; //"redirect:/admin/role/list";
		
	}
	
	
	@GetMapping(path="/validate-role-code",name="Validate Role Code")
	public @ResponseBody String validateUser(String roleCode) {
		JSONObject jsonObject=new JSONObject();
		ServiceOutcome<Role> roleOutcome=roleService.getRoleByCode(roleCode);
		if(roleOutcome.getData()!=null) {
			jsonObject.put("isDuplicate", true);
		}else {
			jsonObject.put("isDuplicate", false);
		}
		return jsonObject.toString();
		
	}
}
