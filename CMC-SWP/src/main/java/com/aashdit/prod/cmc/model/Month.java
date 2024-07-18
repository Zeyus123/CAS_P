package com.aashdit.prod.cmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "t_mst_month")
@Data
public class Month {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "month_id", nullable = false)
    private Long id;

    @Column(name = "month_name")
    private String monthName;

    @Column(name = "month_in_digit")
    private String monthInDigit;

    @Column(name = "month_order_no")
    private Long monthOrderNo;
    
    @Column(name="month_shortcut")
    private String monthShort;

}