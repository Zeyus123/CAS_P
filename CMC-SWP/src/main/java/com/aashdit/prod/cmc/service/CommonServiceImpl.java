package com.aashdit.prod.cmc.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.aashdit.prod.cmc.VO.BankBranchMasterVo;
import com.aashdit.prod.cmc.VO.BankMasterVO;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.AppConfigParameters;
import com.aashdit.prod.cmc.model.BankBranchMaster;
import com.aashdit.prod.cmc.model.BankMaster;
import com.aashdit.prod.cmc.model.Block;
import com.aashdit.prod.cmc.model.DepartmentMaster;
import com.aashdit.prod.cmc.model.District;
import com.aashdit.prod.cmc.model.EmploymentStatus;
import com.aashdit.prod.cmc.model.FinancialYear;
import com.aashdit.prod.cmc.model.umt.Role;
import com.aashdit.prod.cmc.repository.AppConfigParametersRepository;
import com.aashdit.prod.cmc.repository.BankBranchMasterRepository;
import com.aashdit.prod.cmc.repository.BankMasterRepository;
import com.aashdit.prod.cmc.repository.BlockRepository;
import com.aashdit.prod.cmc.repository.DepartmentRespository;
import com.aashdit.prod.cmc.repository.DistrictRepository;
import com.aashdit.prod.cmc.repository.EmploymentStatusRepository;
import com.aashdit.prod.cmc.repository.FinancialYearRepository;
import com.aashdit.prod.cmc.repository.umt.RoleRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class CommonServiceImpl<T> implements CommonService {
//	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";


	@Autowired
	private MessageSource messageSource;

	@Autowired
	private BankMasterRepository bankMstRepo;


	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private BlockRepository blockRepository;

	@Autowired
	private DepartmentRespository depptMstRepo;

	@Autowired
	private BankBranchMasterRepository bankBranchMstRepo;

	@Autowired
	private AppConfigParametersRepository appConfigParamRepo;

	@Autowired
	private EmploymentStatusRepository emplmntTypeRepo;


	@Autowired
	private FinancialYearRepository financialYearRepository;


	@Autowired
	private RoleRepository roleRepository;


	@Autowired
	private BankMasterRepository bankMasterRepository;


	@Override
	public ServiceOutcome<List<District>> findDistrictListByStateId(Long stateId) {
		ServiceOutcome<List<District>> svcOutcome = new ServiceOutcome<>();
		List<District> districts = null;
		try {
			districts = districtRepository.findAllByStateId(stateId, true);
			svcOutcome.setData(districts);
			svcOutcome.setOutcome(true);
		} catch (Exception e) {
			log.error("Exception Occured in findDistrictListByStateId()", e);
			svcOutcome.setData(new ArrayList<District>());
			svcOutcome.setOutcome(false);
		}
		return svcOutcome;
	}

	@Override
	public List<Block> getAllBlockList(Long distId) {
		return blockRepository.findAllByDistrictDistrictIdAndIsActiveTrue(distId);
	}

	@Override
	public List<District> getAllDistrictNameList() {
		return districtRepository.findAllByIsActiveTrueOrderByDistrictName();
	}

	@Override
	public ServiceOutcome<List<BankBranchMaster>> findByBankId(Long bankId) {
		ServiceOutcome<List<BankBranchMaster>> srvc = new ServiceOutcome<>();
		try {
			List<BankBranchMaster> branchList = bankBranchMstRepo.findByBankIdBankIdAndIsActiveTrue(bankId);
			srvc.setData(branchList);

		} catch (Exception e) {
			srvc.setData(null);
			srvc.setOutcome(false);
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			e.printStackTrace();
			log.error("Error occured in findByBankId in CommonServiceImpl => ", e);
		}
		return srvc;
	}


	@Override
	public ServiceOutcome<String> getIfscByBranchId(Long branchId) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
		try {
			BankBranchMaster bankMaster = bankBranchMstRepo.findByBankBranchId(branchId);
			srvc.setData(bankMaster.getIfscCode());

		} catch (Exception e) {
			srvc.setData(null);
			srvc.setOutcome(false);
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			e.printStackTrace();
			log.error("Error occured in getIfscByBranchId in CommonServiceImpl => ", e);
		}
		return srvc;
	}

	@Override
	public ServiceOutcome<String> getConfigParameterValueFromParamCode(String paramCode) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
		try {
			AppConfigParameters appConfig = appConfigParamRepo.findByParamCode(paramCode);
			srvc.setData(appConfig.getParamValue());
			srvc.setOutcome(true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occured in getConfigParameterValueFromParamCode in CommonServiceImpl => ", e);
		}
		return srvc;
	}




	@Override
	public List<FinancialYear> getAllFinancialYearList() {
		List<FinancialYear> years = null;
		try {
			years = financialYearRepository.findByIsActiveTrueOrderByFinYearDesc();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred in getAllFinancialYearList() of CommonServiceImpl => " + e);
		}
		return years;
	}



	@Override
	@Transactional
	public ServiceOutcome<List<Role>> getUpperLevelRoleListByRoleId(Long roleId) {
		ServiceOutcome<List<Role>> srvc = new ServiceOutcome<>();
		try {
			List<Role> roleList = roleRepository.getUpperLevelRoleListByRoleId(roleId);
			srvc.setData(roleList);
			srvc.setOutcome(true);

		} catch (Exception e) {
			srvc.setOutcome(false);
			srvc.setMessage(null);
			e.printStackTrace();
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			log.error("Exception occurred in getWorkFlowDetails() of CommonServiceImpl => " + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return srvc;
	}


	@Override
	@Transactional
	public ServiceOutcome<List<Role>> getLowerLevelRoleListByRoleId(Long roleId) {
		ServiceOutcome<List<Role>> srvc = new ServiceOutcome<>();
		try {
			List<Role> roleList = roleRepository.getLowerLevelRoleListByRoleId(roleId);
			srvc.setData(roleList);
			srvc.setOutcome(true);

		} catch (Exception e) {
			srvc.setOutcome(false);
			srvc.setMessage(null);
			e.printStackTrace();
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			log.error("Exception occurred in getWorkFlowDetails() of CommonServiceImpl => " + e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return srvc;
	}



	@Override
	public ServiceOutcome<List<BankMasterVO>> getAllBankList() {
		List<BankMasterVO> bankDataList = new ArrayList<>();
		try {
			List<BankMaster> bankList = Optional.ofNullable(bankMasterRepository.findAllByIsActiveTrueOrderByBankName())
					.orElse(Collections.emptyList());

			if (bankList.size() > 0) {
				for (BankMaster bank : bankList) {
					BankMasterVO brankVo = new BankMasterVO();
					brankVo.setBankId(bank.getBankId());
					brankVo.setBankName(bank.getBankName());
					brankVo.setShortName(bank.getShortName());
					bankDataList.add(brankVo);
				}
			} else {
				return new ServiceOutcome<>(null, false, "No bank found");
			}


			return new ServiceOutcome<>(bankDataList, true, "fetched");
		} catch (Exception e) {
			log.error("Exception occurred in getAllBankList ==> in CommonService ", e);
			return new ServiceOutcome<>(null, false, "Something went wrong while retrieving bank list.");
		}
	}

	@Override
	public ServiceOutcome<List<BankBranchMasterVo>> getAllBranchByBankId(Long bankId) {
		try {
			List<BankBranchMasterVo> branchList = Optional.ofNullable(bankBranchMstRepo.findByBankIdBankIdAndIsActiveTrue(bankId))
					.map(bankBranchList -> bankBranchList.stream()
							.map(data -> new BankBranchMasterVo(data))
							.collect(Collectors.toList()))
					.orElse(Collections.emptyList());

			return new ServiceOutcome<>(branchList, true, "Bank branches retrieved successfully");
		} catch (Exception e) {
			log.error("Exception occurred in getAllBranchByBankId() ==> in CommonService ", e);
			return new ServiceOutcome<>(null, false, "Something went wrong while retrieving bank list.");
		}
	}


	private boolean matches(Long value1, Long value2) {
		return Optional.ofNullable(value1).map(v1 -> v1.equals(value2)).orElse(true);
	}

}

