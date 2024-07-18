package com.aashdit.prod.cmc.service.umt;

import java.util.List;

import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.umt.Privilege;

public interface PriviledgeService {

	
	ServiceOutcome<List<Privilege>> findAllPriviledges(Boolean includeInActive);
	
	ServiceOutcome<Privilege> findById(Long privilegeId);
	
	ServiceOutcome<Privilege> savePrivilege(Privilege privilege);
	
	ServiceOutcome<String> assignPrivilege(Long roleId, Long menuId, Long privId);
	
	ServiceOutcome<String> revokePrivilege(Long roleId, Long menuId, Long privId);
	
}
