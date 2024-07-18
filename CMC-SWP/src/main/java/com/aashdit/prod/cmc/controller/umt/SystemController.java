package com.aashdit.prod.cmc.controller.umt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Privilege;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.service.umt.MenuService;
import com.aashdit.prod.cmc.service.umt.PriviledgeService;
import com.aashdit.prod.cmc.service.umt.RoleService;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Controller
@RequestMapping(path="/system")
public class SystemController {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PriviledgeService priviledgeService;


	@GetMapping(path="/index", name="System Dashboard")
	private String index(Model model)
	{
		//log.debug(LocaleContextHolder.getLocale().getLanguage());
		/*
		 * ServiceOutcome<List<State>> svcOutcome = accessService.getByRoleLevel(0L, 1L,
		 * AccessLevelType.STATE, State.class); List<State> lstState =
		 * svcOutcome.getData(); lstState = new
		 * LocaleSpecificSorter<State>(State.class).sort(lstState);
		 */
		
		model.addAttribute("lstState", new ArrayList<>());
		
		return "sysAdmin.home";
	}
	
	@GetMapping(path="/setup/module", name="List Module")
	private String setupModule(Model model)
	{
		ServiceOutcome<List<Menu>> serviceOutcome = new ServiceOutcome<>();
		try
		{
			serviceOutcome = menuService.getModuleList();
			model.addAttribute("lstModule", serviceOutcome.getData());
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return "sysAdmin.setup.module";
	}
	
	@GetMapping(path="/setup/module/add", name="Add Module")
	private String addModule(Model model)
	{
		Menu menu = new Menu();
		model.addAttribute("menu", menu);
		
		return "sysAdmin.setup.module.add";
	}
	
	@GetMapping(path="/setup/module/edit/{id}", name="Edit Module")
	private String editModule(@PathVariable Long id, Model model)
	{
		ServiceOutcome<Menu> svcOutcome = new ServiceOutcome<>();
		try
		{
			svcOutcome = menuService.getModule(id);
			if (svcOutcome.getOutcome() == true)
			{
				model.addAttribute("menu", svcOutcome.getData());
			}
			else
			{
				model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
				
		return "sysAdmin.setup.module.edit";
	}
	
	@PostMapping(path="/setup/module/save", name="Save Module")
	private String saveModule(RedirectAttributes redirectAttr, Menu menu)
	{
		ServiceOutcome<Menu> serviceOutcome = new ServiceOutcome<>();
		try
		{
			serviceOutcome = menuService.saveModule(menu);
			if (serviceOutcome.getOutcome() ==  true)
			{
				redirectAttr.addFlashAttribute("success_msg", messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
			}
			else
			{
				redirectAttr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			redirectAttr.addFlashAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()) );
		}
		return "redirect:/system/setup/module";
	}
	
	/* Menu Population from System */
	@GetMapping(path="/setup/menu/init", name="Populate Menu")
	private String setupMenuInit(Model model)
	{
		ServiceOutcome<List<Menu>> serviceOutcome = new ServiceOutcome<>();
		try
		{
			serviceOutcome = menuService.getAppMenuList(true);
			List<Menu> lstMenus = serviceOutcome.getData();
			User user = SecurityHelper.getCurrentUser();
			if (user.getPrimaryRole() != null && !user.getPrimaryRole().getRoleCode().equals("ROLE_SYSTEM"))
			{
				lstMenus = lstMenus.stream().filter(m -> !m.getMenuURL().startsWith("/system")).collect(Collectors.toList());
			}
			
			model.addAttribute("lstMenu", lstMenus);
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return "sysAdmin.setup.menu";
	}
	
	@GetMapping(path="/setup/menu/edit/{id}", name="Edit Menu")
	private String editMenu(@PathVariable Long id, Model model)
	{
		ServiceOutcome<Menu> svcOutcome = new ServiceOutcome<>();
		try
		{
			svcOutcome = menuService.getModule(id);
			if (svcOutcome.getOutcome() == true)
			{
				model.addAttribute("menu", svcOutcome.getData());
			}
			else
			{
				model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
				
		return "sysAdmin.setup.menu.edit";
	}
	
	@PostMapping(path="/setup/menu/save", name="Save Menu")
	private String saveMenu(RedirectAttributes redirectAttr, Menu menu)
	{
		ServiceOutcome<Menu> serviceOutcome = new ServiceOutcome<>();
		try
		{
			serviceOutcome = menuService.saveMenu(menu);
			if (serviceOutcome.getOutcome() ==  true)
			{
				redirectAttr.addFlashAttribute("success_msg", messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
			}
			else
			{
				redirectAttr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
			}
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			redirectAttr.addFlashAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()) );
		}
		return "redirect:/system/setup/menu/list";
	}
	
	@GetMapping(path="/setup/menu/list", name="List Menus")
	private String listMenu(Model model)
	{
		ServiceOutcome<List<Menu>> serviceOutcome = new ServiceOutcome<>();
		try
		{
			serviceOutcome = menuService.getAppMenuList(false);
			List<Menu> lstMenus = serviceOutcome.getData();
			User user = SecurityHelper.getCurrentUser();
			if (user.getPrimaryRole() != null && !user.getPrimaryRole().getRoleCode().equals("ROLE_SYSTEM"))
			{
				lstMenus = lstMenus.stream().filter(m -> !m.getMenuURL().startsWith("/system")).collect(Collectors.toList());
			}
			
			model.addAttribute("lstMenu", lstMenus);
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			model.addAttribute("error_msg",messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
		}
		
		return "sysAdmin.setup.menu";
	}
	
	@GetMapping(path="/setup/menu/add", name="Add Sub Menu")
	private String addMenu(Model model)
	{
		Menu menu = new Menu();
		model.addAttribute("menu", menu);
		
		ServiceOutcome<List<Menu>> serviceOutcome = menuService.getModuleList();
		model.addAttribute("lstModule", serviceOutcome.getData());
		
		return "sysAdmin.setup.menu.add";
	}
	
	@GetMapping(path="/setup/menu/map", name="Super User Role Menu Mapping")
	private String mapRoleMenu(Model model)
	{
		ServiceOutcome<List<Role>> svcOutcome = new ServiceOutcome<>();
		svcOutcome = roleService.getAllRoles(false);
		
		if (svcOutcome.getOutcome() == true)
		{
			List<Role> roleList = svcOutcome.getData();
			model.addAttribute("roleList", roleList);
		}
		else
		{
			model.addAttribute("error_msg", svcOutcome.getMessage());
		}
		
		
		return "admin.menu.role.map";
	}

	@GetMapping(path="/setup/privilege/list",name="List Privilege")
	private String listPrivilege(Model model)
	{
		
		try
		{
			ServiceOutcome<List<Privilege>> svcOutcome = priviledgeService.findAllPriviledges(true);
			if (svcOutcome.getOutcome())
			{
				List<Privilege> lstPrivileges = svcOutcome.getData();
				model.addAttribute("lstPrivileges", lstPrivileges);
			}
			else
			{
				model.addAttribute("error_msg", svcOutcome.getMessage());
				model.addAttribute("lstPrivileges", null);
			}
			
		}
		catch(Exception ex)
		{
			log.error(ex);
			
			model.addAttribute("error_msg", messageSource.getMessage("app.system.error", null, LocaleContextHolder.getLocale()));
			model.addAttribute("lstPrivileges", null);
		}
		
		return "system.privilege.list";
	}
	
	@GetMapping(path="/setup/privilege/edit/{id}", name="Edit Priviledge")
	private String editPrivilege(@PathVariable Long id, Model model)
	{
		ServiceOutcome<Privilege> svcOutcome = new ServiceOutcome<Privilege>();
		try
		{
			svcOutcome = priviledgeService.findById(id);
			if (svcOutcome.getOutcome())
			{
				Privilege privilege = svcOutcome.getData();
				model.addAttribute("privilege", privilege);
			}
			else
			{
				model.addAttribute("error_msg", svcOutcome.getMessage());
				model.addAttribute("lstPrivileges", null);
			}

		}
		catch(Exception ex)
		{
			log.error(ex);
			
			model.addAttribute("error_msg", messageSource.getMessage("app.system.error", null, LocaleContextHolder.getLocale()));
			model.addAttribute("privilege", null);
		}
		
		return "system.privilege.edit";
	}
	
	@PostMapping(path="/setup/privilege/save", name="Save Priviledge")
	private String savePrivilege(@ModelAttribute Privilege privilege, RedirectAttributes attr)
	{
		ServiceOutcome<Privilege> svcOutcome = new ServiceOutcome<Privilege>();
		try
		{
			svcOutcome = priviledgeService.savePrivilege(privilege);
			if (svcOutcome.getOutcome())
			{
				attr.addFlashAttribute("success_msg", messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale()));
			}
			else
			{
				attr.addFlashAttribute("error_msg", svcOutcome.getMessage());
			}

		}
		catch(Exception ex)
		{
			log.error(ex);
			
			attr.addFlashAttribute("error_msg", messageSource.getMessage("app.system.error", null, LocaleContextHolder.getLocale()));
		}
		
		return "redirect:/system/setup/privilege/list";
	}
	
	@GetMapping(path="/setup/privilege/add", name="Add Privilege")
	public String addPrivilege(Model model)
	{
		Privilege privilege =new Privilege();
		model.addAttribute("privilege", privilege);
		
		return "system.privilege.add";
	}
	


}
