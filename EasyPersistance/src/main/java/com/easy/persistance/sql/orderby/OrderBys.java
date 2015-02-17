package com.easy.persistance.sql.orderby;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.IAssembleSql;
import com.easy.persistance.sql.table.ITable;

public class OrderBys implements IAssembleSql{

	private LinkedList<OrderBy> orderByList = new LinkedList<OrderBy>();
	private ITable table;
	public OrderBys(){}
	
	public OrderBys(ITable table){
		this.table = table;
	}
	public void addFirstOrderBy(String colName){
	
		orderByList.add(0,new OrderBy(new Field(table,colName)));
	}
	public void addOrderBy(String... colName){
		for(String c:colName){
			orderByList.add(new OrderBy(new Field(table, c)));
		}
	}
	public void addOrderBy(boolean isDesc,String... colName){
		for(String c:colName){
			orderByList.add(new OrderBy(new Field(table,c),isDesc));
		}
	}
	public int getSize() {
		return orderByList.size();
	}
	public void addOrderBy(List<OrderBy> list){
		for(OrderBy o:list){
			orderByList.add(o);
		}
	}
	public LinkedList<OrderBy> getOrderByList() {
		return orderByList;
	}

	public void clear() {
		orderByList.clear();
	}
	@Override
	public String getAssembleSql(boolean isShowTableAlias){
		Iterator<OrderBy> it = orderByList.iterator();
		StringSplitUtil ss = new StringSplitUtil(); 
		while(it.hasNext()) {
			ss.append(it.next().getOrderBySql(isShowTableAlias));
		}
		return ss.toString();
	}
}
