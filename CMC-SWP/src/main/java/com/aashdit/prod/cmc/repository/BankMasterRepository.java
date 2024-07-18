package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.BankMaster;

@Repository
public interface BankMasterRepository extends JpaRepository<BankMaster, Long>{

	List<BankMaster> findAllByIsActiveTrue(); 

	BankMaster findByBankId(Long benBankId);
	
	List<BankMaster> findAllByIsActiveTrueOrderByBankName();
	
	List<BankMaster> findAll();

	BankMaster findByBankNameAndBankIdNotIn(String upperCase, List<Long> ids);

	BankMaster findByBankName(String upperCase);

	BankMaster findByShortName(String upperCase);

	BankMaster findByShortNameAndBankIdNotIn(String upperCase, List<Long> ids);

}
