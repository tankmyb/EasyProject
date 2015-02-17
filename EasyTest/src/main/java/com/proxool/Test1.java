package com.proxool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.logicalcobwebs.proxool.ProxoolException;

public class Test1 {

	public static void test()throws SQLException, ProxoolException{
Connection connection =ProxoolConnectionFactory.getConnection();
		
		PreparedStatement stmt=connection.prepareStatement("select * from endw_appattr");
		ResultSet rs =stmt.executeQuery();
		//if(rs.next()){
			//System.out.println(rs.getString(1));
		//}
		ProxoolConnectionFactory.close(rs);
		ProxoolConnectionFactory.close(stmt);
		
	}
	public static void main(String[] args) throws SQLException, ProxoolException {
		Long start = System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			test();
		}
		
		System.err.println(System.currentTimeMillis() - start);
		ProxoolConnectionFactory.clearAll();
		ProxoolConnectionFactory.monitor();
		
		ProxoolConnectionFactory.monitor();
	}
}
