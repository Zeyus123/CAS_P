package com.aashdit.prod.cmc.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aashdit.prod.cmc.VO.BankBranchMasterVo;
import com.aashdit.prod.cmc.VO.BankMasterVO;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.BankBranchMaster;
import com.aashdit.prod.cmc.model.Block;
import com.aashdit.prod.cmc.model.DepartmentMaster;
import com.aashdit.prod.cmc.model.District;
import com.aashdit.prod.cmc.model.Grampanchayat;
import com.aashdit.prod.cmc.model.Village;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.service.BlockService;
import com.aashdit.prod.cmc.service.CommonService;
import com.aashdit.prod.cmc.service.GrampanchayatService;
import com.aashdit.prod.cmc.service.VillageService;
import com.aashdit.prod.cmc.utils.ApplicationConstants;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;
import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("/core")
@Slf4j
public class CommonController 
{
	@Autowired
	private BlockService blockService;
	
	@Autowired
	private GrampanchayatService grampanchayatService;

	@Autowired
	private VillageService villageService;
	
	@Autowired
	private CommonService commonService;
	
	SimpleDateFormat sdfDdMmYyyy = new SimpleDateFormat("dd/MM/yyyy");

	
	

	/**
	 * @author Sabita Jena
	 * @since  29/04/2023
	 * @return find District List By StateId
	 */
	@GetMapping("/findDistrictListByStateId")
	public ServiceOutcome<List<District>> findDistrictListByStateId(Long stateId, Model model) 
	{
		ServiceOutcome<List<District>> svcOutcome = new ServiceOutcome<>();

		try {
			svcOutcome = commonService.findDistrictListByStateId(stateId);
		} catch (Exception e) {
			log.error("Exception Occured in getReportNamesByReportLevel()",e);
			svcOutcome.setData(new ArrayList<>());
			svcOutcome.setOutcome(true);
		}

		return svcOutcome;
	}

	/**
	 * @author Sabita Jena
	 * @since  29/04/2023
	 * @return find Block List By DistrictId
	 */
	@GetMapping(path = "/findBlockListByDistrictId", name = "find Block list by District id")
	public @ResponseBody String findBlockListByDistrictId(Long districtId) 
	{
		JSONArray array = new JSONArray();

		try {
			List<Block> blockList = blockService.getAllActiveBlockByDistrictId(districtId,true).getData();
			if (blockList != null) 
			{
				blockList.forEach(block -> 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("blockId", block.getBlockId());
					jsonObject.put("blockName", block.getBlockNameEN());
					array.put(jsonObject);
				});
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return array.toString();
	}

	/**
	 * @author Sabita Jena
	 * @since  29/04/2023
	 * @return find Gp List By BlockId
	 */
	@GetMapping(path = "/findGpListByBlockId", name = "find Gp list by block id")
	public @ResponseBody String findGpListByBlockId(Long blockId) 
	{
		JSONArray array = new JSONArray();

		try 
		{
			List<Grampanchayat> grampanchayatList = grampanchayatService.getAllActiveGpByBlockId(blockId,false).getData();
			if (grampanchayatList != null) 
			{
				grampanchayatList.forEach(grampanchayat -> 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("gpId", grampanchayat.getGpId());
					jsonObject.put("gpName", grampanchayat.getGpNameEN());
					array.put(jsonObject);
				});
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return array.toString();
	}

	/**
	 * @author Sabita Jena
	 * @since  29/04/2023
	 * @return find Village List By GpId
	 */
	@GetMapping(path = "/findVillageListByGpId", name = "find Village list by gp id")
	public @ResponseBody String findVillageListByGpId(Long gpId) 
	{
		JSONArray array = new JSONArray();

		try 
		{
			List<Village> villageList = villageService.getAllActiveVillageByGpId(gpId,false).getData();
			if (villageList != null) 
			{
				villageList.forEach(village -> 
				{
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("villageId", village.getVillageId());
					jsonObject.put("villageName", village.getVillageNameEn());
					array.put(jsonObject);
				});
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return array.toString();
	}
	
	
	 @GetMapping("/bank-list")
	@ResponseBody
		public ResponseEntity<?> getAllBankList(){
			ServiceOutcome<List<BankMasterVO>> soc = new ServiceOutcome<List<BankMasterVO>>();
			try {
				soc = commonService.getAllBankList();
				return ResponseEntity.ok(soc);
			} catch (Exception e) {
				log.error("Exception occurred in getAllBankList() -> CommonController" + e.getMessage());
			}
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
		}

	 
	 @GetMapping("/getbranch-bankId")
		@ResponseBody
			public ResponseEntity<?> getBranchDeatilsByBankId(Long bankId){
				ServiceOutcome<List<BankBranchMasterVo>> soc = new ServiceOutcome<List<BankBranchMasterVo>>();
				try {
					soc = commonService.getAllBranchByBankId(bankId);
					return ResponseEntity.ok(soc);
				} catch (Exception e) {
					log.error("Exception occurred in getBranchDeatilsByBankId() -> CommonController" + e.getMessage());
				}
				return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
			}
	 

	@GetMapping(path = "/getBranchBybankId")
	public @ResponseBody ServiceOutcome<List<BankBranchMaster>> getBranchByBankName(Long bankId) {
		return commonService.findByBankId(bankId);
	}

	@GetMapping(path = "/getIfscByBranchId")
	public @ResponseBody ServiceOutcome<String> getIfscByBranchId(Long branchId) {
		return commonService.getIfscByBranchId(branchId);
	}
	

}

