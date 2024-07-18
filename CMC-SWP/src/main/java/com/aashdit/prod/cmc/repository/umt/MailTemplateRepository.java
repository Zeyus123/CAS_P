package com.aashdit.prod.cmc.repository.umt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aashdit.prod.cmc.model.umt.MailTemplatesMaster;


@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplatesMaster, Long> {

	MailTemplatesMaster findByTemplateTypeAndTemplateCode(String templateType,String templateCode);

}
