package com.aashdit.prod.cmc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aashdit.prod.cmc.service.CommonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/ajax")
@RestController
public class AjaxController {
	
	@Autowired
	private CommonService commonService;
	
	

}
