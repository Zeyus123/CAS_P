package com.aashdit.prod.cmc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

//@ToString

//@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
@Entity
@Table(name = "t_mst_finyear")
@Data
@NoArgsConstructor
public class FinancialYear extends Auditable<User>  implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	@Column(name = "finyear_id")
	private Long finyearId;
	
	@NotNull
	@Column(name = "fin_year")
	private String finYear;

	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@Column(name = "curr_fin_year")
	private Boolean currFinYear;
	
    @Column(name = "prv_fin_year")
    private Long prvFinYr;
    
    @Column(name="fy_short") 
    private String fyShort;
	
	@Transient
	private String previousFinYear;

	
}