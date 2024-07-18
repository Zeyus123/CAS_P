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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_android_version")
public class AppVersion extends Auditable<User> implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -61048195952550432L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "version_id", nullable = false)
    private Long versionId;

    @Column(name = "version_code")
    private String versionCode;

    @Column(name = "is_force_update")
    private Boolean isForceUpdate;

    @Column(name = "is_recommended_update")
    private Boolean isRecommendedUpdate;

    @Column(name = "is_active")
    private Boolean isActive;


}