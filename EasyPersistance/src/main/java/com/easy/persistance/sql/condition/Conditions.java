package com.easy.persistance.sql.condition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.cenum.ConditionEnum;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.IAssembleSql;
import com.easy.persistance.sql.select.ISelect;
import com.easy.persistance.sql.table.ITable;

public class Conditions implements IAssembleSql{

	private LinkedList<ICondition> conditionList = new LinkedList<ICondition>();
	private List<Object> values = new ArrayList<Object>();
	private ITable table;
	public Conditions(){
		
	}
	public Conditions(ITable table){
		this.table = table;
	}
	public <T> void addEqualCondition(String colName,T val) {
		ICondition condition = new Condition(new Field(table,colName),ConditionEnum.EQUAL.getValue(),val);
		conditionList.add(condition);
		values.addAll(condition.getValues());
	}
	public <T> void addInSubSelectCondition(String colName, ISelect select)  {
		ICondition condition = new SubSelectCondition(new Field(table,colName),ConditionEnum.IN.getValue(),select);
		conditionList.add(condition);
		values.addAll(condition.getValues());
	}
	public <T> void addNotEqualCondition(String colName,T val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.NOTEQUAL.getValue(),val);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public <T> void addLessCondition(String colName,T val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.LESS.getValue(),val);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public <T> void addLessEqualCondition(String colName,T val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.LESSEQAL.getValue(),val);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public <T> void addMoreCondition(String colName,T val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.MORE.getValue(),val);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public <T> void addMoreEqualCondition(String colName,T val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.MOREEQUAL.getValue(),val);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	
	public void addLikeCondition(String colName,String val) {
		ICondition w = new Condition(new Field(table,colName),ConditionEnum.LIKE.getValue(),"%"+val+"%");
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public <T> void addBetweenCondition(String colName,T startValue,T endValue) {
		ICondition bw = new BetweenCondition(new Field(table,colName),startValue,endValue);
		conditionList.add(bw);
		values.addAll(bw.getValues());
	}
	
	/**
	 * in 数组
	 * @param field
	 * @param array
	 */
	public <T> void addInArrayCondition(String colName,T[] array) {
		ICondition w = new InCondition(new Field(table,colName),array);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	/**
	 * in List
	 * @param field 字段名
	 * @param list  
	 */
	public <T> void addInListCondition(String colName,List<T> list){
		Object[] valueArray = (Object[])list.toArray(new Object[list.size()]);
		ICondition w = new InCondition(new Field(table,colName),valueArray);
		conditionList.add(w);
		values.addAll(w.getValues());
	}
	public void addIsNullCondition(String colName) {
		ICondition w = new NullCondition(new Field(table,colName),ConditionEnum.IS.getValue());
		conditionList.add(w);
	}
	public void addIsNotNullCondition(String colName) {
		ICondition w = new NullCondition(new Field(table,colName),ConditionEnum.ISNOT.getValue());
		conditionList.add(w);
	}
	
	
	public void addCondition(ICondition logicTree) {
		conditionList.add(logicTree);
		values.addAll(logicTree.getValues());
	}
	public void addCondition(List<ICondition> list){
		for(ICondition w:list){
			conditionList.add(w);
			values.addAll(w.getValues());
		}
	}
	public int getSize() {
		return conditionList.size();
	}

	public void clear() {
		conditionList.clear();
	}
	public LinkedList<ICondition> getConditionList() {
		return conditionList;
	}
	@Override
	public String getAssembleSql(boolean isShowTableAlias){
		Iterator<ICondition> it = conditionList.iterator();
		StringSplitUtil ss = new StringSplitUtil(" and "); 
		while(it.hasNext()) {
			ICondition condition = it.next();
			ss.append(condition.getConditionSql(isShowTableAlias));
			
		}
		return ss.toString();
	}
	public ITable getTable() {
		return table;
	}
	public void setTable(ITable table) {
		this.table = table;
	}
	public List<Object> getValues() {
		return values;
	}
}
