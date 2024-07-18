package com.aashdit.prod.cmc.repository.umt;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aashdit.prod.cmc.model.umt.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
	
	@Query("FROM UserLoginHistory WHERE loggedInDate between :fromDate and :toDate")
	List<UserLoginHistory> findHistoryBetweenLoggedinDate(@Param("fromDate") Date fromDate, @Param("toDate")Date toDate);

}
