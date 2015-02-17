package com.easy.persistance.sql.groupby;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.IAssembleSql;
import com.easy.persistance.sql.table.ITable;
import com.easy.persistance.sql.table.Table;

public class GroupBys implements IAssembleSql{

	private LinkedList<GroupBy> groupByList = new LinkedList<GroupBy>();
	private ITable table;
	public GroupBys(){
		
	}
	public GroupBys(ITable table){
		this.table = table;
	}
	public void addGroupBy(String... colName) {
		for (String c : colName) {
			groupByList.add(new GroupBy(new Field(table,c)));
		}

	}

	public int getSize() {
		return groupByList.size();
	}

	public void addGroupBy(List<GroupBy> groupBys) {
		for (GroupBy g : groupBys) {
			groupByList.add(g);
		}
	}

	public LinkedList<GroupBy> getGroupByList() {
		return groupByList;
	}

	public void clear() {
		groupByList.clear();
	}
	@Override
	public String getAssembleSql(boolean isShowTableAlias) {
		Iterator<GroupBy> it = groupByList.iterator();
		StringSplitUtil ss = new StringSplitUtil();
		while (it.hasNext()) {
			ss.append(it.next().getGroupBySql(isShowTableAlias));
		}
		return ss.toString();
	}

	public ITable getTable() {
		return table;
	}
	public void setTable(ITable table) {
		this.table = table;
	}

}
