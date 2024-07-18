package com.aashdit.prod.cmc.controller.umt;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.framework.core.util.PasswordValidator;
import com.aashdit.prod.cmc.framework.core.util.ViewDocuments;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.service.umt.UserService;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

@Controller
public class UserController {
	
	private final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path="/public/umt/user/registration",name="User List")
	public String userRegistration(Model model) {
		
		return "site.umt.public.registration";
	}
	
	
	@PostMapping(path="/public/umt/user/registration",name="User Registration")
	public String userRegistration(RedirectAttributes attr,User user) {
		
			ServiceOutcome<User> userServiceOutcome=userService.userRegistration(user);
			if (userServiceOutcome.getOutcome()) {
				
				attr.addFlashAttribute("success_msg", userServiceOutcome.getMessage());
			}else {
				attr.addFlashAttribute("error_msg", userServiceOutcome.getMessage());
			}
		
		return "redirect:/public/umt/user/registration";
		
	}
	
	@GetMapping(path="/umt/user/change/password",name="Change Password")
	public String changePassword(Model model) {
		
		User user=SecurityHelper.getCurrentUser();
		ServiceOutcome<User> outcome=userService.findByUserId(user.getUserId());
		model.addAttribute("userDetails", outcome.getData());
		
		return "site.umt.changePassword";
		
	}
	
	@PostMapping(path="/umt/user/change/password",name="Change Password")
	public String changePassword(RedirectAttributes attr,HttpSession session,String userId,String txtPass,String txtRePass) {
		try{
			User user=null;
			if (userId != null) {
				user = userService.findByUsername(userId).getData();
			}
			boolean chk = false;
			if(txtRePass != null && !txtRePass.trim().isEmpty() && txtPass != null && !txtPass.trim().isEmpty()){
				chk = PasswordValidator.checkString(txtPass);
				if (chk) {
					if (txtPass.equals(txtRePass)) {
					       Boolean isSuccess=userService.saveResetPassword(user.getUserId(),txtRePass);
							 
							if(isSuccess) {
								session.invalidate();
								return "redirect:/umt/login?logout";
							}else {
								return "redirect:/umt/user/change/password";
							}
							
						}else{
							attr.addFlashAttribute("error_msg","Password not match with password!");
							log.error("Error Msg : Password not match with password!");
							return "redirect:/umt/user/change/password";
						}
				}else{
					attr.addFlashAttribute("error_msg","Password not match with password policy !");
					log.error("Error Msg : Password not match with password policy !");
					return "redirect:/umt/user/change/password";
				}
			}else{
				attr.addFlashAttribute("error_msg","Password not match with password policy !");
				log.error("Error Msg : Password not match with password policy !");
				return "redirect:/umt/user/change/password";
			}
		}catch(Exception e){
			log.error(e.getMessage());
			attr.addFlashAttribute("error_msg", "Unable to update profile");
			return "redirect:/umt/user/change/password";
		}
		
	}
		
	
	@PostMapping(path="/umt/user/profile/update",name="Update Profile")
	public String updateProfile(RedirectAttributes attr ,User user,MultipartFile userProfileImage) {
		
		ServiceOutcome<User> userServiceOutcome=userService.updateProfile(user,userProfileImage);
		if (userServiceOutcome.getOutcome()) {
			
			attr.addFlashAttribute("success_msg", userServiceOutcome.getMessage());
		}else {
			attr.addFlashAttribute("error_msg", userServiceOutcome.getMessage());
		}
		
		return "redirect:/umt/user/profile";
		
	}
	
	@GetMapping(path="/umt/user/image/view",name="View User Image")
	public void viewImage(Model model,HttpServletRequest request,HttpServletResponse response) {
		User user=SecurityHelper.getCurrentUser();
	   ServiceOutcome<User> userDtls=userService.findByUserId(user.getUserId());
	   if((userDtls.getData().getProfilePhoto()!=null))
	   ViewDocuments.viewUploadedDocument(userDtls.getData().getProfilePhoto(),request,response);
		
	}
	
	@GetMapping(path="/umt/user/list",name="User List")
	public String userList(Model model, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) Integer page,  @RequestParam(required = false) Integer size) {
		
		if (page == null) page = 0;
		if (size == null) size = 10;
		
		PageRequest pageRequest = PageRequest.of(page, size);
		
		ServiceOutcome<Page<User>> outcome=userService.findUserList(searchTerm, pageRequest );
		model.addAttribute("userList", outcome.getData());
		
		model.addAttribute("searchTerm", searchTerm);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		
		return "site.umt.userList";
		
	}
	
	@PostMapping(path="/umt/user/list",name="Search User List")
	public String postUserList(Model model, @RequestParam(required = false) String searchTerm, @RequestParam(required = false) Integer page,  @RequestParam(required = false) Integer size) {
		
		if (page == null) page = 0;
		if (size == null) size = 10;
		
		PageRequest pageRequest = PageRequest.of(page, size);
		
		ServiceOutcome<Page<User>> outcome=userService.findUserList(searchTerm, pageRequest );
		model.addAttribute("userList", outcome.getData());
		
		model.addAttribute("searchTerm", searchTerm);
		model.addAttribute("page", page);
		model.addAttribute("size", size);
		
		return "site.umt.userList";
		
	}
	
	@GetMapping(path="/umt/user/profile",name="User Profile")
	public String userProfile(Model model) {
		User user=SecurityHelper.getCurrentUser(); 
		
		ServiceOutcome<User> outcome=userService.findByUserId(user.getUserId());
		model.addAttribute("userDetails", outcome.getData());
		
		return "site.umt.userProfile";
		
	}

	@GetMapping(path="/umt/user/add",name="Add user")
	public String addUser(Model model) {
		
		  List<Role> roleList=userService.findActiveRole();
		  model.addAttribute("roleList", roleList);
		
		return "site.umt.addUser";
		
	}
	
	@GetMapping(path="/umt/user/edit/{id}",name="Edit User")
	public String userEdit(Model model,@PathVariable("id")Long userId) {
		
		ServiceOutcome<User> outcome=userService.findByUserId(userId);
		model.addAttribute("serviceOutcome", outcome);
		
		List<UserRoleMap> userRoleMaps=userService.findUserRoleMapByUserId(userId);
		model.addAttribute("userRoleHcMapList", userRoleMaps);
				
		List<Role> roleList=userService.findActiveRole();
		model.addAttribute("roleList", roleList);
		
		return "site.umt.userEdit";
		
	}
	
	@GetMapping(path="/umt/user/view/{id}",name="View User")
	public String userView(Model model,@PathVariable("id")Long userId) {
		
		ServiceOutcome<User> outcome=userService.findByUserId(userId);
		model.addAttribute("serviceOutcome", outcome);
		
		//Added for Bug Fix. Need to see Role details too
		
		List<UserRoleMap> userRoleMaps=userService.findUserRoleMapByUserId(userId);
		model.addAttribute("userRoleHcMapList", userRoleMaps);
				
		List<Role> roleList=userService.findActiveRole();
		model.addAttribute("roleList", roleList);
		
		return "site.umt.userView";
		
	}
	
	@PostMapping(path="/umt/user/isActive",name="Active And Inactive")
	public String lockNUnlock(RedirectAttributes attr,Long userId,Boolean isActive) {
		
		ServiceOutcome<User> serviceOutcome=userService.lockNUnlockUserById(userId,isActive);
		 if(serviceOutcome.getOutcome()) {
			   attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
		   }else {
			   attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
		   }
		
		return "redirect:/umt/user/list";
		
	}
	
	@PostMapping(path="/umt/user/update",name="Update User")
	public String userUpdate(RedirectAttributes attr,Long userid,String username,String firstname,String lastname,
			Date dateOfbirth,String userMobile ,String userEmail,@RequestParam("roleName[]")Long roleName[],
				@RequestParam("isPrimary[]")Long isPrimary[],@RequestParam(value="status[]",required=false)String status[],
				@RequestParam("userRoleHcMapId[]")Long userRoleHcMapId[], @RequestParam String designation) {
		
		ServiceOutcome<User> serviceOutcome=userService.updateUser(userid,username,firstname,lastname,dateOfbirth,userMobile,userEmail,userRoleHcMapId,roleName,isPrimary,status, designation);
		 if(serviceOutcome.getOutcome()) {
			   attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
			   return "redirect:/umt/user/edit/" + serviceOutcome.getData().getUserId();
		   }else {
			   attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
			   return "redirect:/umt/user/add";
		   }
		
		 
	}
	
	@PostMapping(path="/umt/user/add",name="Add user")
	public String addUser(RedirectAttributes attr,String username,String firstname,String lastname,
			Date dateOfbirth,String userMobile ,String userEmail,@RequestParam("roleName[]")Long roleName[],@RequestParam("isPrimary[]")Long isPrimary[], @RequestParam String designation) {
		
		ServiceOutcome<User> serviceOutcome=userService.addUser(username,firstname,lastname,dateOfbirth,userMobile,userEmail,roleName,isPrimary,designation,"",designation, 0L,new Long[]{0L},null);
	  
	   if(serviceOutcome.getOutcome()) {
		   attr.addFlashAttribute("success_msg", serviceOutcome.getMessage());
		   return "redirect:/umt/user/edit/" + serviceOutcome.getData().getUserId();
	   }else {
		   attr.addFlashAttribute("error_msg", serviceOutcome.getMessage());
		   return "redirect:/umt/user/add";
	   }
		
	}
	
	@GetMapping(path="/umt/user/validate-user-name",name="Validate User Name")
	public @ResponseBody String validateUser(String userName) {
		JSONObject jsonObject=new JSONObject();
		ServiceOutcome<User> userOutcome=userService.findByUsername(userName.trim());
		if(userOutcome.getData()!=null) {
			jsonObject.put("isDuplicate", true);
		}else {
			jsonObject.put("isDuplicate", false);
		}
		
		return jsonObject.toString();
		
	}
}
