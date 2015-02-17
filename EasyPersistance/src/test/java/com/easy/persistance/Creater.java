package com.easy.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import com.easy.persistance.common.ConnectionFactory;
import com.easy.persistance.creater.ACreate;
import com.easy.persistance.creater.dao.CreateBaseDAO;
import com.easy.persistance.creater.metadata.CreateMetaData;
import com.easy.persistance.creater.row.CreateRow;
import com.easy.persistance.creater.select.CreateSelect;
import com.easy.persistance.util.CreateUtil;

public class Creater {

	public static void main(String[] args)throws SQLException {
        //"course","mark","student"
		//String[] tableName = new String[] { "mark_view"};
		Connection conn = ConnectionFactory.currentConnection();
		String[] tableName = CreateUtil.getAllTableAndView(conn);
		try {
			String packageName = "com.easy.persistance.orm";
			String parentPath = "../EasyPersistance/src/test/java/";

			ACreate cb = new CreateRow(conn, parentPath, tableName, packageName);
			cb.create();

			ACreate cmd = new CreateMetaData(conn, parentPath, tableName, packageName);
			cmd.create();

		    ACreate cs = new CreateSelect(conn, parentPath, tableName, packageName);
			cs.create();
			
			ACreate cd = new CreateBaseDAO(conn, parentPath, tableName, packageName);
			cd.create();
		} finally {
			ConnectionFactory.closeConnection();
		}
	}
}
