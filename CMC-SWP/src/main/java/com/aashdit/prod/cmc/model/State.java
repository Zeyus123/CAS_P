package com.aashdit.prod.cmc.model;

import java.io.Serializable;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import com.aashdit.prod.cmc.framework.core.annotation.Sortable;
import com.aashdit.prod.cmc.framework.core.model.Auditable;
import com.aashdit.prod.cmc.model.umt.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_mst_state")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class State extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = 285701719160134651L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private Long stateId;

	@Column(name = "state_name_en")
	@Sortable(lang = "en")
	private String stateNameEN;

	@Column(name = "state_name_hi")
	@Sortable(lang = "hi")
	private String stateNameHI;

	@Column(name = "state_code")
	private String stateCode;

	@Column(name = "is_active")
	private Boolean isActive;
	
	@Transient
	private String stateName;

	public String getStateName() {
		Locale locale = LocaleContextHolder.getLocale();
		switch (locale.getLanguage()) {
		case "en":
			return this.getStateNameEN();
		case "hi":
			return this.getStateNameHI();
		default:
			return this.getStateNameEN();
		}
	}

}
