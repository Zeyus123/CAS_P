package com.aashdit.prod.cmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_hr_tour_purpose_mst")
public class Purpose {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="purpose_id")
	private Long purposeId;
	
	@Column(name="purpose_name")
	private String purposeName;
	
    @Column(name = "is_active")
	private Boolean isActive=true;

}
