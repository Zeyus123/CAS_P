package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.EmploymentStatus;

@Repository
public interface EmploymentStatusRepository extends JpaRepository<EmploymentStatus, Long>
{
	List<EmploymentStatus> findAllByIsActiveTrueAndShowInStaffMstTrueOrderByDisplayOrder();

	List<EmploymentStatus> findAllByIsActiveTrueAndShowInPayrollTrueOrderByDisplayOrder();

	EmploymentStatus findByEmploymentId(Long employmentId);

	EmploymentStatus findByEmploymentCode(String employmentCode);

	EmploymentStatus findByEmploymentCodeAndIsActiveTrueAndShowInPayrollTrue(String string);


}
