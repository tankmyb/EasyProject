package com.easy.persistance.sql.condition.on;

import java.util.Iterator;
import java.util.LinkedList;

import com.easy.kit.utils.string.StringSplitUtil;
import com.easy.persistance.sql.Field;
import com.easy.persistance.sql.condition.FieldCondition;
import com.easy.persistance.sql.condition.ICondition;

public class Ons {

	private LinkedList<IOn> onList = new LinkedList<IOn>();

	public void addOnCondition(Field leftField,Field rightField,String joinType) {
		onList.add(new On(leftField,rightField,joinType));
	}
	public void addRightOnCondition(Field leftField,Field rightField,String joinType) {
		onList.add(new RightOn(leftField,rightField,joinType));
	}
	public void addOnCondition(Field leftField,Field rightField,ICondition logic,String joinType) {
		onList.add(new LogicOn(leftField,rightField,logic,joinType));
	}
	public void addOnCondition(Field leftField,Field rightField) {
		ICondition logic = new FieldCondition(leftField, rightField);
		IOn on = new OnCondition(logic);
		onList.add(on);
	}
	public void addOnCondition(ICondition logic) {
		IOn on = new OnCondition(logic);
		onList.add(on);
	}
	public int getSize() {
		return onList.size();
	}

	public void clear() {
		onList.clear();
	}
	public IOn getOn(int index){
		return onList.get(index);
	}
	public String getAssemble(){
		Iterator<IOn> it = onList.iterator();
		StringSplitUtil ss = new StringSplitUtil(" "); 
		while(it.hasNext()) {
			ss.append(it.next().getOnSql());
		}
		return ss.toString();
	}
}
