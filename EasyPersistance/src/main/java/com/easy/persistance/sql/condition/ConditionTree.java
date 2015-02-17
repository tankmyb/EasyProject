package com.easy.persistance.sql.condition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.easy.kit.utils.string.StringSplitUtil;

public class ConditionTree implements ICondition {

	private LinkedList<ICondition> whereList = new LinkedList<ICondition>();
	private List<Object> values = new ArrayList<Object>();
	private boolean isOr = true;

	public ConditionTree(ICondition... wheres) {
		this(true, wheres);
	}

	public ConditionTree(boolean isOr, ICondition... wheres) {
		this.isOr = isOr;
		for (ICondition w : wheres) {
			whereList.add(w);
			values.addAll(w.getValues());
		}
	}

	private String getLogic() {
		if (isOr) {
			return " or ";
		}
		return " and ";
	}

	@Override
	public String getConditionSql(boolean isShowTableAlias) {
		if (whereList.size() == 0) {
			throw new RuntimeException("没有条件");
		}
		String logic = this.getLogic();
		StringBuffer sb = new StringBuffer("(");
		StringSplitUtil ssu = new StringSplitUtil(logic);
		for (ICondition w : whereList) {
			ssu.append(w.getConditionSql(isShowTableAlias));
		}
		sb.append(ssu.toString()).append(")");
		return sb.toString();
	}

	public List<Object> getValues() {
		return values;
	}

}
