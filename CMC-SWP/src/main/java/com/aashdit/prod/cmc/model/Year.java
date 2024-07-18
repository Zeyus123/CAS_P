package com.aashdit.prod.cmc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.Data;

@Data
@Entity
@Table(name="t_mst_year")	
public class Year extends Auditable<User> implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 772778374754020937L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "year_id", nullable = false)
    private Long yearId;
    
    @Column(name = "year_name")
    private String yearName;
    
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "is_active")
    private Boolean isActive=true;

}
