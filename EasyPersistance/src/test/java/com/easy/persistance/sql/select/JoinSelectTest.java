package com.easy.persistance.sql.select;

import javax.annotation.Resource;

import org.junit.Test;

import com.easy.persistance.BaseTest;
import com.easy.persistance.common.SQLAssistant;
import com.easy.persistance.dao.IJdbcTemplateEx;
import com.easy.persistance.resultset.DBResultSet;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
import com.easy.persistance.sql.table.Table;

public class JoinSelectTest  extends BaseTest {

	@Resource
	private IJdbcTemplateEx jdbcTemplateEx; 

	@Test
	public void testGetSQL() {
		MysqlSelect s1 = new MysqlSelect(new Table("mark","m"));
		s1.addAllColumn();
		s1.addEqualCondition("ma_stu_no", "a");
		MysqlSelect s2 = new MysqlSelect(new Table("student","s"));
		s2.addAllColumn();
		s2.addEqualCondition("stu_no", "a");
		JoinSelect js = new JoinSelect(s1,s2);
		js.addOnCondition(s1.createField("ma_stu_no"), s2.createField("stu_no"),SQLAssistant.INNERJOIN );
		System.out.println(js.getSQL());
		DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(js.getSQL(), js.getValues());
		System.out.println(dbrs.size());
	}

}
