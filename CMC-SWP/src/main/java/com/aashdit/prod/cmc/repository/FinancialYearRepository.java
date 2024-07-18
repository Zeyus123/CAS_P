package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.FinancialYear;

@Repository
public interface FinancialYearRepository extends JpaRepository<FinancialYear, Long> {

	List<FinancialYear> findByIsActiveTrueOrderByFinYearDesc();

	FinancialYear findByCurrFinYearTrue();

	@Query("From FinancialYear where finyearId in (?1,?2)")
	List<FinancialYear> getFinListById(Long finYear2, Long finYear);

//	@Query("From FinancialYear where finYear=:fnYear")
//	FinancialYear findByFinYear(@Param("fnYear")String fnYear);

    List<FinancialYear> findAllByIsActiveTrue();

	List<FinancialYear> findAllByIsActiveTrueOrderByPrvFinYrDesc();

	List<FinancialYear> findAllByIsActiveTrueOrderByPrvFinYrAsc();

	FinancialYear findByFinYear(String finYear);

	@Query(value="select * from t_mst_finyear where fin_year=:fnYear",nativeQuery = true)
	FinancialYear getfinYearData(@Param("fnYear") String finYr);

	FinancialYear findByFinyearId(Long finId);

	List<FinancialYear> findAllByIsActiveTrueAndCurrFinYearTrue();

	List<FinancialYear> findByIsActiveTrueOrderByFinYear();

	@Query("FROM FinancialYear WHERE  finYear=:financialYear")
	FinancialYear getFinYrDetailsByYear(@Param("financialYear") String financialYear);
}

