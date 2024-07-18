package com.aashdit.prod.cmc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.umt.UserRepository;

@Service
public class UserApplicationMapServiceImpl implements UserApplicationMapService {

	
//	@Autowired
//    private UserApplicationMapServiceRepository mapRepository;
	
	@Autowired
    private UserRepository userRepository;
	
	
	@Override
	public List<String> getAccessableModule(String username) {
		List<String>  modulesNames=new ArrayList<>();
		try {
			User user = userRepository.findByUserName(username);	
//			List<UserApplicationMap> userMap = mapRepository.findMapData(user.getPrimaryRole().getRoleId());	
//	    	modulesNames=userMap.stream().map(data->data.getModuleName()).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modulesNames;
	}

}
