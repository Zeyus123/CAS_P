package com.aashdit.prod.cmc.provider;

import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Provider {

    final static Logger logger = Logger.getLogger(Provider.class);

    @PersistenceContext
    private EntityManager em;


    public static String getAllCardData(String roleLevel, Long user, Long demographyId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT\n");
        query.append("bnf.total_bnf,\n");
        query.append("bnf.total_drct_bnf,\n");
        query.append("bnf.total_indrct_bnf,\n");
        query.append("expndtr.total_expndtr,\n");
        query.append("expndtr.monthly_expnd,\n");
        query.append("expndtr.qtrly_expnd,\n");
        query.append("rvnu.total_rvnu,\n");
        query.append("rvnu.monthly_rvnu,\n");
        query.append("rvnu.qtrly_rvnu,\n");
        query.append("others.total_clstr\n");
        query.append("FROM\n");
        query.append("(\n");
        query.append("-- Beneficiary totals subquery\n");
        query.append("SELECT\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND benf.district_id = :demographyId AND benf.spv_id =:user THEN 1\n");
        query.append("WHEN :roleLevel = 'STATE' AND benf.district_id = :demographyId THEN 1\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_bnf,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'STATE' AND benf.state_id = :demographyId AND benf.is_active = true AND bentyp.ben_type_code = 'DB' THEN 1\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND benf.district_id = :demographyId AND benf.is_active = true AND bentyp.ben_type_code = 'DB' AND benf.spv_id = :user THEN 1\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_drct_bnf,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'STATE' AND benf.state_id = :demographyId AND benf.is_active = true AND bentyp.ben_type_code = 'IDB' THEN 1\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND benf.district_id = :demographyId AND benf.is_active = true AND bentyp.ben_type_code = 'IDB' AND benf.spv_id = :user THEN 1\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_indrct_bnf\n");
        query.append("FROM\n");
        query.append("t_ben_beneficiary benf\n");
        query.append("LEFT JOIN\n");
        query.append("t_mst_beneficiary_type bentyp ON benf.beneficiary_type = bentyp.ben_type_id\n");
        query.append(") AS bnf\n");
        query.append("CROSS JOIN\n");
        query.append("(\n");
        query.append("-- Expenditure totals subquery\n");
        query.append("SELECT\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND expndtr.spv_id = :user AND expndtr.is_active = true THEN expndtr.expen_amount\n");
        query.append("WHEN :roleLevel = 'STATE' AND expndtr.is_active = true THEN expndtr.expen_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_expndtr,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND expndtr.spv_id = :user AND expndtr.is_active = true AND DATE_TRUNC('month', expndtr.date_of_expen) = DATE_TRUNC('month', CURRENT_DATE) THEN expndtr.expen_amount\n");
        query.append("WHEN :roleLevel = 'STATE' AND expndtr.is_active = true AND DATE_TRUNC('month', expndtr.date_of_expen) = DATE_TRUNC('month', CURRENT_DATE) THEN expndtr.expen_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS monthly_expnd,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND expndtr.spv_id = :user AND expndtr.is_active = true AND DATE_PART('quarter', expndtr.date_of_expen) = DATE_PART('quarter', CURRENT_DATE) AND DATE_PART('year', expndtr.date_of_expen) = DATE_PART('year', CURRENT_DATE) THEN expndtr.expen_amount\n");
        query.append("WHEN :roleLevel = 'STATE' AND expndtr.is_active = true AND DATE_PART('quarter', expndtr.date_of_expen) = DATE_PART('quarter', CURRENT_DATE) AND DATE_PART('year', expndtr.date_of_expen) = DATE_PART('year', CURRENT_DATE) THEN expndtr.expen_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0.0) AS qtrly_expnd\n");
        query.append("FROM\n");
        query.append("t_fin_expenditure expndtr\n");
        query.append(") AS expndtr\n");
        query.append("CROSS JOIN\n");
        query.append("(\n");
        query.append("-- Revenue totals subquery\n");
        query.append("SELECT\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' and incm.spv_id = :user and incm.is_active = true THEN incm.income_amount\n");
        query.append("WHEN :roleLevel = 'STATE' and incm.is_active = true THEN incm.income_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_rvnu,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND incm.spv_id = :user AND incm.is_active = true AND DATE_TRUNC('month', incm.income_date) = DATE_TRUNC('month', CURRENT_DATE) THEN incm.income_amount\n");
        query.append("WHEN :roleLevel = 'STATE' AND incm.is_active = true AND DATE_TRUNC('month', incm.income_date) = DATE_TRUNC('month', CURRENT_DATE) THEN incm.income_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS monthly_rvnu,\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN :roleLevel = 'DISTRICT' AND incm.spv_id = :user AND incm.is_active = true AND DATE_PART('quarter', incm.income_date) = DATE_PART('quarter', CURRENT_DATE) AND DATE_PART('year', incm.income_date) = DATE_PART('year', CURRENT_DATE) THEN incm.income_amount\n");
        query.append("WHEN :roleLevel = 'STATE' AND incm.is_active = true AND DATE_PART('quarter', incm.income_date) = DATE_PART('quarter', CURRENT_DATE) AND DATE_PART('year', incm.income_date) = DATE_PART('year', CURRENT_DATE) THEN incm.income_amount\n");
        query.append("ELSE 0\n");
        query.append("END), 0.0) AS qtrly_rvnu\n");
        query.append("FROM\n");
        query.append("t_fin_add_income incm\n");
        query.append(") AS rvnu\n");
        query.append("CROSS JOIN\n");
        query.append("(\n");
        query.append("-- Cluster totals subquery\n");
        query.append("SELECT\n");
        query.append("COALESCE(SUM(CASE\n");
        query.append("WHEN clstr.is_active = true THEN 1\n");
        query.append("ELSE 0\n");
        query.append("END), 0) AS total_clstr\n");
        query.append("FROM\n");
        query.append("t_mst_cluster clstr\n");
        query.append(") AS others;\n");

        logger.trace(query.toString());
        return query.toString();
    }
}
