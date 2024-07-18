package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.DepartmentMaster;

@Repository
public interface DepartmentRespository extends JpaRepository<DepartmentMaster, Long> {

	DepartmentMaster findByDepartmentId(Long deptId);
	
	List<DepartmentMaster> findAllByIsActiveTrue();
	
	DepartmentMaster findByDepartmentNameAndDepartmentIdNotIn(String upperCase, List<Long> ids);

	DepartmentMaster findByDepartmentName(String upperCase);

	DepartmentMaster findByDepartmentCode(String upperCase);

	DepartmentMaster findByDepartmentCodeAndDepartmentIdNotIn(String upperCase, List<Long> ids);

	List<DepartmentMaster> findAllByDepartmentIdIn(List<Long> deptIds);
}
