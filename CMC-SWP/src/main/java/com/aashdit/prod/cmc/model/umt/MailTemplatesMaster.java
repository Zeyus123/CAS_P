package com.aashdit.prod.cmc.model.umt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.aashdit.prod.cmc.framework.core.model.Auditable;

import lombok.Data;

@Entity
@Data
@Table(name = "t_ws_mail_templates")
public class MailTemplatesMaster extends Auditable<User> implements Serializable {

	private static final long serialVersionUID = -6974103749030920423L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long messageId;

	@Column(name = "template_name")
	private String templateName;

	@Column(name = "template_id")
	private String templateId;

	@Column(name = "template_type")
	private String templateType;
	
	@Column(name = "template_code")
	private String templateCode;
	
}
