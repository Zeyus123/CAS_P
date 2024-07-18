package com.aashdit.prod.cmc.VO.umt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONArray;

public class AccesslevelConfigMetaInfo implements Serializable {

	private static final long serialVersionUID = 5850565114020440305L;
	
	private String primaryKey;
	
	private List<Long> allotedRowIds;
	
	private String data;
	
	private LinkedHashMap<Integer, String> columnMetaData;
	
	private Integer currentPage ;
	
	private Integer totalPages;
	
	private Integer pageSize;
	
	public AccesslevelConfigMetaInfo()
	{
		this.allotedRowIds = new ArrayList<Long>();
		this.columnMetaData = new LinkedHashMap<Integer, String>();
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}


	public List<Long> getAllotedRowIds() {
		return allotedRowIds;
	}

	public void setAllotedRowIds(List<Long> allotedRowIds) {
		this.allotedRowIds = allotedRowIds;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public LinkedHashMap<Integer, String> getColumnMetaData() {
		return columnMetaData;
	}

	public void setColumnMetaData(LinkedHashMap<Integer, String> columnMetaData) {
		this.columnMetaData = columnMetaData;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	


	
	

}
