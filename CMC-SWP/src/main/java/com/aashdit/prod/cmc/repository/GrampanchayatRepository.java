package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Grampanchayat;

@Repository
public interface GrampanchayatRepository extends JpaRepository<Grampanchayat, Long> {

	Grampanchayat findByGpId(Long gpId);

	List<Grampanchayat> findAllByBlockBlockIdAndIsActiveTrue(Long demoId);

	@Query("FROM Grampanchayat where block.blockId=:blockId and isActive = :isActive ORDER BY gpNameEN")
	List<Grampanchayat> findAllByBlockId(@Param("blockId") Long blockId, @Param("isActive") Boolean isActive);

	@Query(value="select * from t_mst_gramapanchayat gp where gp.is_active=true", nativeQuery = true)
	 List<Grampanchayat> findByIsActiveTrueGpId(Long gpId);
}
