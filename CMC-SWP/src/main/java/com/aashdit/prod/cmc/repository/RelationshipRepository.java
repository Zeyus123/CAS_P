package com.aashdit.prod.cmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.Relationship;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long>{

	List<Relationship> findAllByIsActiveTrue();

	Relationship findByRelationshipId(Long relationshipId);

}
