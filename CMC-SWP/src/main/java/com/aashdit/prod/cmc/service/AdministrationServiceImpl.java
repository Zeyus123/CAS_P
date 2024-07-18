package com.aashdit.prod.cmc.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.aashdit.prod.cmc.VO.BankBranchDTO;
import com.aashdit.prod.cmc.framework.core.ServiceOutcome;
import com.aashdit.prod.cmc.model.BankBranchMaster;
import com.aashdit.prod.cmc.model.BankMaster;
import com.aashdit.prod.cmc.model.DepartmentMaster;
import com.aashdit.prod.cmc.model.umt.User;
import com.aashdit.prod.cmc.repository.BankBranchMasterRepository;
import com.aashdit.prod.cmc.repository.BankMasterRepository;
import com.aashdit.prod.cmc.repository.DepartmentRespository;
import com.aashdit.prod.cmc.utils.umt.SecurityHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdministrationServiceImpl implements AdministrationService,MessageSourceAware{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private DepartmentRespository departmentRespository;
	
	@Autowired
	private BankBranchMasterRepository bankBranchMasterRepository;
	
	@Autowired
	private BankMasterRepository bankMasterRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	} 
	


	
	@Override
	public ServiceOutcome<DepartmentMaster> saveDepartment(DepartmentMaster dept) {
		ServiceOutcome<DepartmentMaster> servc=new ServiceOutcome<>();
		
		try {
			User user=SecurityHelper.getCurrentUser();
			String msg ="";
			
			servc = new ServiceOutcome<>();
			DepartmentMaster deptData=null;
			if(Optional.ofNullable(dept.getDepartmentId()).isPresent()) {
				
				deptData=departmentRespository.findByDepartmentId(dept.getDepartmentId());
				msg = messageSource.getMessage("msg.success", null, LocaleContextHolder.getLocale());
     			deptData.setLastUpdatedBy(user);
				deptData.setLastUpdatedOn(new Date());
			}else {
				deptData = new DepartmentMaster();
				//deptData.setEcpenditureCode("EXPEN-"+RandomStringUtils.random(6, true, true).toUpperCase());
				 deptData.setCreatedBy(user);
				 msg = messageSource.getMessage("msg.success.dept", null, LocaleContextHolder.getLocale());
				 deptData.setCreatedOn(new Date());
			}
			deptData.setDepartmentName(dept.getDepartmentName().trim().toUpperCase());
			deptData.setDepartmentCode(dept.getDepartmentCode().trim().toUpperCase());
			departmentRespository.save(deptData);
			servc.setData(deptData);
			servc.setOutcome(true);
			servc.setMessage(msg);
			
		} catch (Exception e) {
			e.printStackTrace();
			servc.setOutcome(false);
			servc.setData(null);
			servc.setMessage(messageSource.getMessage("msg.error.dept", null, LocaleContextHolder.getLocale()));
			log.error("Exception occured ",e);
		}
		return servc;
	}

	@Override
	public List<DepartmentMaster> getAllDeptList(Boolean dependStatus) {
		List<DepartmentMaster> getAllDeptList = new ArrayList<>();
		try {
			if(dependStatus) {
				getAllDeptList = departmentRespository.findAllByIsActiveTrue();
			}else {
				getAllDeptList = departmentRespository.findAll();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return getAllDeptList;
	}
	
	@Override
	public ServiceOutcome<DepartmentMaster> editDepartment(Long deptId) {
		ServiceOutcome<DepartmentMaster> srvc = new ServiceOutcome<>();
		try {
			if(Optional.ofNullable(deptId).isPresent()); {
				DepartmentMaster dept = departmentRespository.findById(deptId).get();
					srvc.setData(dept);
					srvc.setOutcome(true);
			}
		} catch(Exception e) {
			srvc.setOutcome(false);
			srvc.setData(null);
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			e.printStackTrace();
			log.error("error while editing the data  " +e);
		}
		return srvc;
	}
	
	@Override
	public ServiceOutcome<String> updateDepartment(Long deptId, Boolean status) {
		
		return null;
	}
	
	@Override
	public ServiceOutcome<DepartmentMaster> manageDepartment() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BankMaster> getAllBankNames() {
		return bankMasterRepository.findAllByIsActiveTrue();
	}

	@Override
	public ServiceOutcome<BankBranchDTO> saveBankBranchData(BankBranchDTO bankBranchDTO, String status) {
	    ServiceOutcome<BankBranchDTO> outcome = new ServiceOutcome<>();
	    User user = SecurityHelper.getCurrentUser();

	    try {
	        // Check if the provided bankBranchDTO has an ID (indicating an update)
	        if (bankBranchDTO.getBankBranchMaster().getBankBranchId() != null) {
	            // Update existing data
	            BankBranchMaster existingBranch = bankBranchMasterRepository.findByBankBranchId(bankBranchDTO.getBankBranchMaster().getBankBranchId());
	            if (existingBranch != null) {
	                // Update the existing branch data here
	                existingBranch.setBranchName(bankBranchDTO.getBankBranchMaster().getBranchName());
	                existingBranch.setIfscCode(bankBranchDTO.getBankBranchMaster().getIfscCode());
	                existingBranch.setBranchAddress(bankBranchDTO.getBankBranchMaster().getBranchAddress());
	                existingBranch.setBranchEmail(bankBranchDTO.getBankBranchMaster().getBranchEmail());
	                existingBranch.setBranchMobile(bankBranchDTO.getBankBranchMaster().getBranchMobile());
	                existingBranch.setLastUpdatedBy(user);
	                existingBranch.setLastUpdatedOn(new Date());

	                // Save the updated entity to the database
	                bankBranchMasterRepository.save(existingBranch);

	                outcome.setData(bankBranchDTO);
	                outcome.setMessage("Bank branch data update successful.");
	                outcome.setOutcome(true);
	            } else {
	                outcome.setData(null);
	                outcome.setMessage("Bank branch with ID " + bankBranchDTO.getBankBranchMaster().getBankBranchId() + " not found.");
	                outcome.setOutcome(false);
	            }
	        } else {
	            // No ID present, check if a branch with the same bank ID exists
//	            List<BankBranchMaster> existingBranches = bankBranchMasterRepository.findByBankIdBankId(bankBranchDTO.getBankBranchMaster().getBankId().getBankId());
//
//	            if (existingBranches != null && !existingBranches.isEmpty()) {
//	                // Handle the case where a branch with the same bank ID already exists
//	                outcome.setData(null);
//	                outcome.setMessage("A branch with the same bank ID already exists.");
//	                outcome.setOutcome(false);
//	            } else {
	                // Create a new BankBranch entity and populate its fields
	                BankBranchMaster newBranch = new BankBranchMaster();
	                newBranch.setBankId(bankMasterRepository.findByBankId(bankBranchDTO.getBankBranchMaster().getBankId().getBankId()));
	                newBranch.setBranchName(bankBranchDTO.getBankBranchMaster().getBranchName());
	                newBranch.setIfscCode(bankBranchDTO.getBankBranchMaster().getIfscCode());
	                newBranch.setBranchAddress(bankBranchDTO.getBankBranchMaster().getBranchAddress());
	                newBranch.setBranchEmail(bankBranchDTO.getBankBranchMaster().getBranchEmail());
	                newBranch.setBranchMobile(bankBranchDTO.getBankBranchMaster().getBranchMobile());
	                newBranch.setIsActive(true);
	                newBranch.setCreatedBy(user);
	                newBranch.setCreatedOn(new Date());

	                bankBranchMasterRepository.save(newBranch);

	                outcome.setData(bankBranchDTO);
	                outcome.setMessage("Bank branch data save successful.");
	                outcome.setOutcome(true);
	            }
	        // }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        // Log the specific exception details for debugging
	        outcome.setData(null);
	        outcome.setMessage("The save process was unsuccessful. Please try again later.");
	        outcome.setOutcome(false);
	    }

	    return outcome;
	}



	@Override
	public ServiceOutcome<BankBranchDTO> editBranchMaster(BankBranchDTO bankBranchDTO) {
		ServiceOutcome<BankBranchDTO> srvc = new ServiceOutcome<>();
		try {
			if(Optional.ofNullable(bankBranchDTO.getBankBranchMaster().getBankBranchId()).isPresent()) {
				BankBranchMaster brchmaster=bankBranchMasterRepository.findByBankBranchId(bankBranchDTO.getBankBranchMaster().getBankBranchId());
				bankBranchDTO.setBankBranchMaster(brchmaster);
			}
			srvc.setData(bankBranchDTO);
			srvc.setOutcome(true);
		} catch (Exception e) {
			srvc.setOutcome(false);
			srvc.setData(null);
			srvc.setMessage(messageSource.getMessage("msg.error", null, LocaleContextHolder.getLocale()));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			log.error("Exception occured in editBranchMaster ",e);
		}
		return srvc;
	}

	@Override
	public ServiceOutcome<BankBranchDTO> getBranchMasterData() {
		ServiceOutcome<BankBranchDTO> servc = new ServiceOutcome<>();
		BankBranchDTO brchVo = new BankBranchDTO();
		try {
			brchVo.setBankBranchMasterList(bankBranchMasterRepository.findAll());
			servc.setData(brchVo);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occured in getBranchMasterData of AdministratorServiceImpl => "+e.getMessage());
		}
		return servc;
	}

	@Override
	public List<BankMaster> getAllBankMasterList() {
		
		return bankMasterRepository.findAll();
	}

	@Override
	public ServiceOutcome<String> saveBankMaster (BankMaster bm) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
		try {
			BankMaster bcm = null;
			if(Optional.ofNullable(bm.getBankId()).isPresent()) {
				bcm = bankMasterRepository.findById(bm.getBankId()).get();
				srvc.setMessage(messageSource.getMessage("msg.success.bankUpdt", null, LocaleContextHolder.getLocale()));
			}else {
				bcm = new BankMaster();
				srvc.setMessage(messageSource.getMessage("msg.success.bank", null, LocaleContextHolder.getLocale()));
			}
			bcm.setBankName(bm.getBankName().trim().toUpperCase());
			bcm.setShortName(bm.getShortName().trim().toUpperCase());
			bankMasterRepository.save(bcm);
			srvc.setOutcome(true);
		} catch (Exception e) {
			srvc.setOutcome(false);
			srvc.setData(null);
			e.printStackTrace();
			log.error("error while saving the data "+e);
		}
		return srvc;
	}

	@Override
	public ServiceOutcome<BankMaster> editBankMaster (Long bankId) {
		ServiceOutcome<BankMaster> srvc = new ServiceOutcome<>();
		try {
			if(Optional.ofNullable(bankId).isPresent()) {
				BankMaster bcm = bankMasterRepository.findById(bankId).get();
				srvc.setData(bcm);
				srvc.setOutcome(true);
			}
		}catch(Exception e) {
			srvc.setOutcome(false);
			srvc.setData(null);
			e.printStackTrace();
			log.error("error while editing the data  " +e);
		}
		
		return srvc;
	
	}

	@Override
	public ServiceOutcome<String>  updateBankMasterStatus(Long bankId, Boolean status) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
		try {
			if(Optional.ofNullable(bankId).isPresent()) {
				BankMaster bcm = bankMasterRepository.findById(bankId).get();
				bcm.setIsActive(status);
				bankMasterRepository.save(bcm);
				srvc.setOutcome(true);
				if(status) {
					srvc.setMessage(messageSource.getMessage("msg.active.bank", null, LocaleContextHolder.getLocale()));
				}else {
					srvc.setMessage(messageSource.getMessage("msg.inactive.bank", null, LocaleContextHolder.getLocale()));
				}
			}
		}catch(Exception e) {
				srvc.setOutcome(false);
				srvc.setData(null);
				e.printStackTrace();
				log.error("error while updating status of Bank Master  " +e);
			}
		return srvc;
	}

	@Override
	public ServiceOutcome<String> checkStatusOfBranch(Long bankBranchId ,Boolean status) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
	    try {
	    	if(Optional.ofNullable(bankBranchId).isPresent()) {
	    		BankBranchMaster brchData = bankBranchMasterRepository.findByBankBranchId(bankBranchId);
                brchData.setIsActive(status);
                bankBranchMasterRepository.save(brchData);
                if (status) {
                    srvc.setMessage(messageSource.getMessage("msg.active.branch", null, LocaleContextHolder.getLocale()));
                } else {
                    srvc.setMessage(messageSource.getMessage("msg.inactive.branch", null, LocaleContextHolder.getLocale()));
                }
                srvc.setOutcome(true);
	        } 
	    } catch (Exception e) {
	        srvc.setOutcome(false);
	        srvc.setData(null);
	        srvc.setMessage("An error occurred while processing the request.");
	        e.printStackTrace();
	        log.error("Exception occurred in bankBranchMasterRepository in AdministratorServiceImpl", e);
	    }
	    return srvc;
	}

	
	@Override
	public ServiceOutcome<String> updateStatusDept(Long deptId, Boolean status) {
		ServiceOutcome<String> srvc = new ServiceOutcome<>();
		try {
			if(deptId != null) {
				DepartmentMaster dept = departmentRespository.findByDepartmentId(deptId);
				dept.setIsActive(status);
				departmentRespository.save(dept);
				if(status) {
					srvc.setMessage(messageSource.getMessage("msg.active.dept", null, LocaleContextHolder.getLocale()));
				}else {
					srvc.setMessage(messageSource.getMessage("msg.inactive.dept", null, LocaleContextHolder.getLocale()));
				}
				srvc.setOutcome(true);
			}
			
		} catch (Exception e) {
			srvc.setOutcome(false);
			srvc.setData(null);
			e.printStackTrace();
			log.error("error while updating status of Department master  " +e);
		}
		return srvc;
	}





}
