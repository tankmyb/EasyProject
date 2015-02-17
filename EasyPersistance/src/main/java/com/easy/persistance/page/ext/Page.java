package com.easy.persistance.page.ext;

import java.util.List;
@SuppressWarnings("unchecked")
public class Page {

	/**
      * 总共记录数
      */
	private int totalCount;
	
	
	private List data;


	public Page(int totalCount,List data){
		this.totalCount = totalCount;
		this.data = data;
	}


	public int getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}


	public List getData() {
		return data;
	}


	public void setData(List data) {
		this.data = data;
	}
	


}
