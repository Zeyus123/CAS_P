package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.BankBranchMaster;


@Repository
public interface BankBranchMasterRepository extends JpaRepository<BankBranchMaster, Long>{

	BankBranchMaster findByBankBranchId(Long branchId);

	List<BankBranchMaster> findByBankIdBankId(Long bankId);

	List<BankBranchMaster> findByBankIdBankIdAndIsActiveTrue(Long bankId);

	List<BankBranchMaster> findByIsActive(boolean b);

}
