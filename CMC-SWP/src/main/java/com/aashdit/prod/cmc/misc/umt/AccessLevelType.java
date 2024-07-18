package com.aashdit.prod.cmc.misc.umt;

public enum AccessLevelType {
	
	STATE("STATE"),
	DISTRICT("DISTRICT"),
	BLOCK("BLOCK"),
	GP("GRAMPANCHAYAT"),
	VILLAGE("VILLAGE"),
	HEAD_QUARTER("HEAD_QUARTER");
	
	
	
	private String accessLevel;
	
	AccessLevelType(String accesslevel)
	{
		this.accessLevel = accesslevel;
	}
	
	public String getAccesslevel()
	{
		return this.accessLevel;
	}
	
}
