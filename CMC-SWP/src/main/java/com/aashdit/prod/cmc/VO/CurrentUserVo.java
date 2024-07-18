package com.aashdit.prod.cmc.VO;

import java.util.List;

import com.aashdit.prod.cmc.model.umt.Menu;
import com.aashdit.prod.cmc.model.umt.Role;

import lombok.Data;

@Data
public class CurrentUserVo {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String userLevel;// COLG or UNV or DEPT
	private String userType;//STAFF or STUDENT or ADMIN
	private String mobile;
	private String email;
	private Long userTypeId;//Staff or student id or (college id in case of admin)
	private Long entityId;//college id or department id or university id
	private String beneficiaryCode;//Staff or Student code
	//private List<DepartmentDetailsVo> staffDepartments;
	private Long staffDepartmentId;
	private Role primaryRole;
	private List<Menu> menuList;
	private String ssoUrl;
	private String jwtForCore;
}
