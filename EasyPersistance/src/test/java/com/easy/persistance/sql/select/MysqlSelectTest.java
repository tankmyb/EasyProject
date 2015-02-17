package com.easy.persistance.sql.select;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.persistance.BaseTest;
import com.easy.persistance.cenum.ConditionEnum;
import com.easy.persistance.dao.IJdbcTemplateEx;
import com.easy.persistance.resultset.DBResultSet;
import com.easy.persistance.sql.condition.Condition;
import com.easy.persistance.sql.condition.ConditionTree;
import com.easy.persistance.sql.condition.ICondition;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
import com.easy.persistance.sql.table.Table;

public class MysqlSelectTest extends BaseTest {

	@Resource
	private IJdbcTemplateEx jdbcTemplateEx; 

	@Test
	public void testCustomColumn() {
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		select.addCustomColumn("LEFT(m.ma_stu_no,2)", "a");
		System.out.println(select.getSQL());
	}
	@Test
	public void testSubSelect() {
		MysqlSelect subselect = new MysqlSelect(new Table("student","s"));
		subselect.addColumns("stu_no");
		subselect.addEqualCondition("stu_no", "sno1");
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		//select.addAllColumn();
		select.addInSubSelectCondition("ma_stu_no",subselect);
		select.addLessEqualCondition("ma_mark", 40);
		//System.out.println(select.getSQL());
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(select.getSQL(), select.getValues());
		System.out.println(dbrs.size());
	}
	@Test
	public void testGetSQL() {
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		select.addCustomColumn("aa", "a");
		select.addEqualCondition("ma_stu_no", "a");
		select.addEqualCondition("ma_cou_no", "a");
		
		List<Object> values = select.getValues();
		for(Object obj:values){
			System.out.println(obj);
		}
		System.out.println(select.getSQL());
	}
	@Test
	public void testQuery() {
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		select.addEqualCondition("ma_stu_no", "a");
		select.addEqualCondition("ma_cou_no", "a");
		select.addLikeCondition("ma_stu_no", "a");
		select.addBetweenCondition("ma_mark", 1, 100);
		//select.addCustomCondition(customColName, value)
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(select.getSQL(), select.getValues());
		System.out.println(dbrs.size());
	}
	@Test
	public void testIn() {
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		select.addInCondition("id",list);
		//select.addInCondition("id", new Integer[]{1,2,3,4});
		//select.addInArrayCondition("ma_stu_no", new String[]{"a","b"});
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(select.getSQL(), select.getValues());
		System.out.println(dbrs.size());
	}
	@Test
	public void testNull() {
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		select.addIsNotNullCondition("ma_stu_no");
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(select.getSQL(), select.getValues());
		System.out.println(dbrs.size());
	}
	@Test
	public void testConditionTree(){
		MysqlSelect select = new MysqlSelect(new Table("mark","m"));
		select.addAllColumn();
		select.addBetweenCondition("ma_mark", 1, 100);
		ICondition c0 = new Condition(select.createField("ma_cou_no"),ConditionEnum.LIKE.getValue(),"a");
		select.addConditions(c0);
		
		ICondition c1 = new Condition(select.createField("ma_stu_no"),ConditionEnum.MORE.getValue(),"a");
		ICondition c2 = new Condition(select.createField("ma_stu_no"),ConditionEnum.EQUAL.getValue(),"b");
		ConditionTree tree = new ConditionTree(c1,c2);
		select.addConditions(tree);
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(select.getSQL(), select.getValues());
		System.out.println(dbrs.size());
	}
	
}
