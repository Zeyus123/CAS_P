package com.aashdit.prod.cmc.model.umt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.aashdit.prod.cmc.VO.CurrentUserVo;

public class LoggedInUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 7767108758059803455L;

	private Role primaryRole;

	private User dbUser;

	private CurrentUserVo currentUserVo;

	public LoggedInUser(String username, String password, boolean enabled, boolean accountNonExpired,
				boolean credentialsNonExpired, boolean accountNonLocked,
				Collection<? extends GrantedAuthority> authorities,  Role primaryRole, User dbUser)
    {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

		
		this.primaryRole = primaryRole;
		//this.userId = userId;
		this.dbUser = dbUser;
		

	}

	public LoggedInUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,  Role primaryRole, User dbUser, CurrentUserVo currentUserVo)
	{
	super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	this.primaryRole = primaryRole;
	this.dbUser = dbUser;
	this.currentUserVo = currentUserVo;
	}

	public Role getPrimaryRole() {
		return primaryRole;
	}
	
	public User getDbUser()
	{
		return dbUser;
	}

	public CurrentUserVo getCurrentUserVo() {
		return currentUserVo;
	}
}
