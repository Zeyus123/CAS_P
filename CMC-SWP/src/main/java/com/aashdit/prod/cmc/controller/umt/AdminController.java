package com.aashdit.prod.cmc.controller.umt;

import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aashdit.prod.cmc.VO.umt.AccesslevelConfigMetaInfo;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.RoleRightLevelMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.service.umt.AccessService;
import com.aashdit.prod.cmc.service.umt.PriviledgeService;
import com.aashdit.prod.cmc.service.umt.RoleRightLevelService;
import com.aashdit.prod.cmc.service.umt.RoleService;
import com.aashdit.prod.cmc.service.umt.UserService;

@Controller
@RequestMapping(path="/admin")
public class AdminController {
	
private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleRightLevelService roleRightLevelService;
	
	@Autowired
	private AccessService accessService;
	
	@Autowired
	private PriviledgeService priviledgeService;
	
	@GetMapping(path="/users/list")
	public String listUsers(Model model)
	{
		
		
		return "site.admin.users.list";
	}
	
	@GetMapping(path="/user/access/configure")
	public String configureAccessLevel(Model model)
	{
		
		return "site.admin.user.acl.configure";
	}
	
	@RequestMapping(path="/user/search", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> searchUser(@RequestParam String qry)
	{
		ServiceOutcome<List<User>> svcOutcome = userService.searchUser(qry);
		return svcOutcome.getData();
		
	}
	
	@RequestMapping(path="/role/list/all", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Role> getAllRoles()
	{
		ServiceOutcome<List<Role>> svcOutcome = roleService.getAllRoles(false);
		List<Role> lstRoles = svcOutcome.getData()
										.stream()
										.filter(r -> (r.getDisplayOnPage() == true))
										.collect(Collectors.toList());
		return lstRoles;
		
	}
	
	@RequestMapping(path="/role/byUser", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Role> getRoleForUser(@RequestParam Long userId )
	{
		ServiceOutcome<List<Role>> svcOutcome = roleService.getRoleForUser(userId);
		return svcOutcome.getData();
	}
	
	@RequestMapping(path="/acl/byRole", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<RoleRightLevelMaster> getACLForRole(@RequestParam Long roleId )
	{
		ServiceOutcome<List<RoleRightLevelMaster>> svcOutcome = roleRightLevelService.getRoleRightLevelsByRoleId(roleId);
		return svcOutcome.getData();
	}
	
	@RequestMapping(path="/acl/config/list", produces=MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	@ResponseBody
	public AccesslevelConfigMetaInfo getConfiguredData(@RequestParam Long userId, @RequestParam Long roleId, @RequestParam Long roleLevelMasterId,
			@RequestParam(required = false) String searchTerm , @RequestParam Integer page, @RequestParam Integer size )
	{
		ServiceOutcome<AccesslevelConfigMetaInfo> svcOutcome = accessService.getACLConfigData(userId, roleId, roleLevelMasterId, searchTerm, page, size);
		
		return svcOutcome.getData();
	}
	
	@RequestMapping(path="/acl/config/save", produces= MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	@ResponseBody
	public ServiceOutcome<String> saveConfig(@RequestParam Long userId, @RequestParam Long roleId, @RequestParam Long roleLevelMasterId, @RequestParam Long objectId)
	{
		ServiceOutcome<String> svcOutcome = accessService.saveConfig(userId, roleId, roleLevelMasterId, objectId);
		
		return svcOutcome;
	}
	
	@GetMapping(path="/acl/list", name="List Access Level Master")
	private String listAccessLevels(Model model)
	{
		ServiceOutcome<List<RoleRightLevelMaster>> svcOutcome = roleRightLevelService.getRoleRightLevels(true);
		model.addAttribute("lstAccessLevels", svcOutcome.getData());
		
		return "site.admin.acl.list";
	}
	
	@GetMapping(path="/menu/map", name="Role Menu Mapping")
	private String mapRoleMenu(Model model)
	{
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		svcOutcome = roleService.getAllRoles(false);
		
		if (svcOutcome.getOutcome() == true)
		{
			List<Role> roleList = svcOutcome.getData();
			//roleList = roleList.stream().filter(f -> !f.getRoleName().equals("ROLE_ADMIN")).collect(Collectors.toList());
			roleList = roleList.stream().filter(f -> !f.getRoleCode().equals("ROLE_SYSTEM")).collect(Collectors.toList());
			
			model.addAttribute("roleList", roleList);
		}
		else
		{
			model.addAttribute("error_msg", svcOutcome.getMessage());
		}
		
		
		return "admin.menu.role.map";
	}
	
	@GetMapping(path="/privilege/map", name="Role Menu to Permission Mapping")
	private String mapRoleMenuPermission(Model model)
	{
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		svcOutcome = roleService.getAllRoles(false);
		
		if (svcOutcome.getOutcome() == true)
		{
			List<Role> roleList = svcOutcome.getData();
			roleList = roleList.stream().filter(f -> !f.getRoleCode().equals("ROLE_ADMIN")).collect(Collectors.toList());
			roleList = roleList.stream().filter(f -> !f.getRoleCode().equals("ROLE_SYSTEM")).collect(Collectors.toList());
			
			model.addAttribute("roleList", roleList);
		}
		else
		{
			model.addAttribute("error_msg", svcOutcome.getMessage());
		}
		
		ServiceOutcome<List<Privilege>> svcOutcome1 = priviledgeService.findAllPriviledges(false);
		if (svcOutcome1.getOutcome() == true)
		{
			List<Privilege> privs = svcOutcome1.getData();
			model.addAttribute("privs", privs);
		}
		else
		{
			model.addAttribute("error_msg", svcOutcome1.getMessage());
		}
		
		return "admin.permission.map";
	}
	
	@PostMapping(path="/privilege/assign", name="Assign Privilege", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private ServiceOutcome<String> assignPrivilege(@RequestParam Long roleId, @RequestParam Long menuId, @RequestParam Long privId)
	{
		ServiceOutcome<String> svcOutcome = priviledgeService.assignPrivilege(roleId, menuId, privId);
		
		return svcOutcome;
	}
	
	@PostMapping(path="/privilege/revoke", name="Remove Privilege", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	private ServiceOutcome<String> revokePrivilege(@RequestParam Long roleId, @RequestParam Long menuId, @RequestParam Long privId)
	{
		ServiceOutcome<String> svcOutcome = priviledgeService.revokePrivilege(roleId, menuId, privId);
		
		return svcOutcome;
	}

}
