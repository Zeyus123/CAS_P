package com.aashdit.prod.cmc.misc.umt;

public enum PrivilegeType {

	ROW("ROW"),
	HEADER("HEADER"),
	FOOTER("FOOTER");
	
	private String privilegeType;
	
	PrivilegeType(String privilegeType)
	{
		this.privilegeType = privilegeType;
	}

	public String getPrivilegeType() {
		return privilegeType;
	}
	
	
}
