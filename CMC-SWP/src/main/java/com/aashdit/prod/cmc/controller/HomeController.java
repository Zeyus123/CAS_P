package com.aashdit.prod.cmc.controller;

import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.framework.core.util.PasswordValidator;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.model.umt.UserLoginHistory;
import com.aashdit.prod.cmc.model.umt.UserRoleMap;
import com.aashdit.prod.cmc.repository.umt.UserLoginHistoryRepository;
import com.aashdit.prod.cmc.service.umt.AccessService;
import com.aashdit.prod.cmc.service.umt.ForgotPasswordService;
import com.aashdit.prod.cmc.service.umt.UserService;
import com.aashdit.prod.cmc.utils.AesUtil;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	ResourceBundle rb = ResourceBundle.getBundle("application");

	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private UserLoginHistoryRepository userLoginHistoryRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccessService accessService;
	
	
	@GetMapping(path = "/home", name = "User Home Page")
	public String home(Model model,HttpServletRequest request,@RequestParam(value="encValue",required = false) String encValue)
	{
		User user = SecurityHelper.getCurrentUser();
		log.info(user.toString());
		if(user.getUserType()==null) {
			return "app.home";
		}
//		ServiceOutcome<List<CommonNameIdDto>> accessList = accessService.getAccessListToShowInDashboard(user);
//		model.addAttribute("accessList", accessList);
//		
//		if(accessList.getOutcome() && accessList.getData()!=null && accessList.getData().size() ==1) {
//			CommonNameIdDto commonNameIdDto=accessList.getData().get(0); 
//			UserRoleMap userRoleMap= accessService.getUserRoleMapByMapId(commonNameIdDto.getId()).getData();
//			updateCurrentUserVo(request,userRoleMap);
//		}

		if(encValue!=null && !encValue.equals("")) {
			byte[] enc = Base64.getDecoder().decode(encValue);
			encValue = new String(enc);
			Long userRoleMapId = Long.parseLong(encValue);
			model.addAttribute("userRoleMapId", userRoleMapId);
			UserRoleMap userRoleMap= accessService.getUserRoleMapByMapId(userRoleMapId).getData();
//			updateCurrentUserVo(request,userRoleMap);
		}
		return "app.home";
	}



	@ExceptionHandler(HttpSessionRequiredException.class)
	public String handleSessionTimeOut() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		return "site.session.timeout";
	}

	@PostMapping(value = "/reset/forgot-password", name = "Reset Password")
	public String forgotPassword(RedirectAttributes attribute, String username) {
		try {
			ServiceOutcome<Boolean> result = forgotPasswordService.forgotPassword(username);
			attribute.addFlashAttribute(result.getData() ? "success_msg" : "err_msg", result.getMessage());
		} catch (Exception e) {
			log.error("Exception occured in forgotPassword method in HomeController-->" + e.getMessage());
		}
		return "redirect:/login";
	}

	@GetMapping(value = "/admin/user/login/history")
	public String userLoginHistory(Model model) {
		try {
			List<UserLoginHistory> userHistories = userLoginHistoryRepository.findAll();
			if (userHistories != null && userHistories.size() > 0) {
				userHistories = userHistories.stream().sorted(Comparator.comparing(UserLoginHistory::getLoggedInDate))
						.collect(Collectors.toList());
				Collections.reverse(userHistories);
				model.addAttribute("userHistoryList", userHistories);
			}
		} catch (Exception e) {
			
			log.error("Exception occured in getting login history in HomeController-->" + e);
		}
		return "site.admin.loginHistory";
	}

	@PostMapping(path = "/umt/user/change/password/submit", name = "Change Password")
	public String changePassword(RedirectAttributes attr, HttpSession session, String userId, String txtPass,
			String txtRePass, String currPass, HttpServletRequest request) {
		try {
			Boolean isOK = Boolean.valueOf(true);
			String realCurrentPass = "";
			String realPass = "";
			String realRePass = "";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User user = null;
			if (userId != null) {
				user = userService.findByUsername(userId).getData();
			} else {
				session.invalidate();
				return "redirect:/login";
			}
			String decryptedCurrentPassword = new String(java.util.Base64.getDecoder().decode(currPass));
			String psk = request.getParameter("_csrf");
			psk = psk.substring(0, 16);
			AesUtil aesUtil = new AesUtil(128, 1000);
			if (decryptedCurrentPassword != null && decryptedCurrentPassword.split("::").length == 3) {
				realCurrentPass = aesUtil.decrypt(decryptedCurrentPassword.split("::")[1],
						decryptedCurrentPassword.split("::")[0], psk, decryptedCurrentPassword.split("::")[2]);
			}
			isOK = Boolean.valueOf(passwordEncoder.matches(realCurrentPass, user.getPassword()));
			if (!isOK.booleanValue()) {
				attr.addFlashAttribute("error_msg", "Sorry, Curent Password not matched please try again !");
				log.error("Error Msg : Sorry, Curent Password not matched please try again during change password !");
				return "redirect:/umt/user/change/password";
			} else {
				boolean chk = false;
				if (txtRePass != null && !txtRePass.trim().isEmpty() && txtPass != null && !txtPass.trim().isEmpty()) {
					String decryptedPassword = new String(java.util.Base64.getDecoder().decode(txtPass));
					if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
						realPass = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0],psk, decryptedPassword.split("::")[2]);
					}
					String decryptedRePassword = new String(java.util.Base64.getDecoder().decode(txtRePass));
					if (decryptedRePassword != null && decryptedRePassword.split("::").length == 3) {
						realRePass = aesUtil.decrypt(decryptedRePassword.split("::")[1],decryptedRePassword.split("::")[0], psk, decryptedRePassword.split("::")[2]);
					}
					chk = PasswordValidator.checkString(realPass);
					if (chk) {
						if (realPass.equals(realRePass)) {
							Boolean isSuccess = userService.saveResetPassword(user.getUserId(), realRePass);
							if (isSuccess) {
								log.info("Password changed successfully.");
								attr.addFlashAttribute("success_msg", "Password changed successfully.");
								session.invalidate();
								return "redirect:/login";
							} else {
								return "redirect:/umt/user/change/password";
							}

						} else {
							attr.addFlashAttribute("error_msg", "Password not match with password!");
							log.error("Error Msg : Password not match with password!");
							return "redirect:/umt/user/change/password";
						}
					} else {
						attr.addFlashAttribute("error_msg", "Password not match with password policy !");
						log.error("Error Msg : Password not match with password policy !");
						return "redirect:/umt/user/change/password";
					}
				} else {
					attr.addFlashAttribute("error_msg", "Password not match with password policy !");
					log.error("Error Msg : Password not match with password policy !");
					return "redirect:/umt/user/change/password";
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			attr.addFlashAttribute("error_msg", "Unable to update profile");
			return "redirect:/umt/user/change/password";
		}

	}

	
}
