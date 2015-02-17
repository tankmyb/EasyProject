package com.easy.persistance.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.easy.persistance.resultset.CustomResultSet;

public class CreateUtil {

	/*
	 Select 表名 = Case When a.Colorder=1 Then d.Name Else '' End,
	表说明 = Case When a.Colorder=1 Then Isnull(f.Value,'') Else '' End,
	字段序号 = a.Colorder,
	字段名 = a.Name,
	标识 = Case When ColumnProperty(a.ID,a.Name,'IsIdentity')=1 Then '√' Else '' End,
	主键 = Case When Exists(Select 1 From Sysobjects Where Xtype='PK' And Name In (Select Name From Sysindexes Where Indid In (Select Indid From Sysindexkeys Where ID=a.ID And Colid=a.Colid))) Then '√' Else '' End,
	类型 = b.Name,
	占用字节数 = a.Length,
	长度 = ColumnProperty(a.ID,a.Name,'Precision'),
	小数位数 = Isnull(ColumnProperty(a.ID,a.Name,'Scale'),0),
	允许空 = Case When a.IsNullable = 1 then '√' else '' end,
	默认值 = Isnull(e.Text,''),
	字段说明 = Isnull(g.Value,'')
	From Syscolumns a
	Left Join Systypes b on a.Xusertype=b.Xusertype
	Inner Join Sysobjects d on a.ID=d.ID And d.Xtype='U' And d.Name <> 'dtproperties'
	Left Join Syscomments e on a.Cdefault=e.ID
	Left Join Sysproperties f on d.ID = f.ID And f.Smallid=0
	left join Sysproperties g on a.ID = g.ID And a.Colid = g.Smallid 
	--Where d.Name='要查询的表'--如果只查询指定表,加上此条件
	Where d.Name='@servicecall'
	Order By a.ID,a.Colorder
	 */
	public static CustomResultSet getMssql2000TableStructure(Connection conn,String tableName){
		StringBuilder sql = new StringBuilder("select \n");
		sql.append("table_desc = cast(Case When a.Colorder=1 Then Isnull(f.Value,'') Else '' End  as VARCHAR),\n");
		sql.append("col_no = cast(a.Colorder as INT),\n");
		sql.append("col_name = a.Name,\n");
		sql.append("is_identity = Case When ColumnProperty(a.ID,a.Name,'IsIdentity')=1 Then 'true' Else 'false' End,\n");
		sql.append("ident_current_value =  Case When ColumnProperty(a.ID,a.Name,'IsIdentity')=1 Then ident_current(d.name) Else 0 End,\n");
		sql.append("ident_incr_value =  Case When ColumnProperty(a.ID,a.Name,'IsIdentity')=1 Then ident_incr(d.name) Else 0 End,\n");
		sql
				.append("is_pk = Case When Exists(Select 1 From Sysobjects Where Xtype='PK' And Name In (Select Name From Sysindexes Where Indid In ");
		sql.append("(Select Indid From Sysindexkeys Where ID=a.ID And Colid=a.Colid))) Then 'true' Else 'false' End,\n");
		sql.append("col_type = b.Name,\n");
		sql.append("col_len = CAST(a.Length AS INT),\n");
		sql.append("col_precision=CAST(COLUMNPROPERTY(a.id,a.name,'PRECISION') AS INT),");
		sql.append("col_decimal=CAST(isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0) as INT),");
		sql.append("is_null = Case When a.IsNullable = 1 then 'true' else 'false' end,\n");
		sql.append("default_value = Isnull(e.Text,''),\n");
		sql.append("col_desc = cast(Isnull(g.Value,'') as VARCHAR) \n");
		sql.append("From Syscolumns a \n");
		sql.append("Left Join Systypes b on a.Xusertype=b.Xusertype \n");
		sql.append("Inner Join Sysobjects d on a.ID=d.ID And d.Xtype='U' And d.Name <> 'dtproperties' \n");
		sql.append("Left Join Syscomments e on a.Cdefault=e.ID \n");
		sql.append("Left Join Sysproperties f on d.ID = f.ID And f.Smallid=0 \n");
		sql.append("left join Sysproperties g on a.ID = g.ID And a.Colid = g.Smallid \n");
		sql.append("Where d.Name='").append(tableName).append("' \n");
		sql.append("Order By a.ID,a.Colorder\n");
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			return new CustomResultSet(rs,false);
		} catch (SQLException e) {
			throw new RuntimeException("executeQuery失败",e);
		}finally{
			JdbcUtil.close(rs);
		}
	}
	
	public static boolean getOracleIsAutoIncrement(Connection conn,String tableName) throws SQLException{
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery("select * from user_objects where object_type = 'SEQUENCE' and object_name ='S_"+tableName+"' ");
			return rs.next();
		} catch (SQLException e) {
			throw e;
		}finally{
			JdbcUtil.close(rs);
		}
	}
	/*
	 * 
	select
    A.column_name 字段名,A.data_type 数据类型,A.data_length 长度,A.data_precision 整数位,
    A.Data_Scale 小数位,A.nullable 允许空值,A.Data_default 缺省值,B.comments 备注
    from
    user_tab_columns A,user_col_comments B
    where
    A.Table_Name = B.Table_Name
    and A.Column_Name = B.Column_Name
    and A.Table_Name = ''''TABLE_TEST''''
	 */
	public static CustomResultSet getOracleTableStructure(Connection conn,String tableName) throws SQLException{
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" A.column_name ,A.data_type ,A.data_length ,A.data_precision ,");
		sql.append(" A.Data_Scale ,A.nullable,A.Data_default,B.comments");
		sql.append(" from");
		sql.append(" user_tab_columns A,user_col_comments B");
		sql.append(" where");
		sql.append(" A.Table_Name = B.Table_Name");
		sql.append(" and A.Column_Name = B.Column_Name");
		sql.append(" and A.Table_Name = '").append(tableName).append("'");
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			return new CustomResultSet(rs,false);
		} catch (SQLException e) {
			throw e;
		}finally{
			JdbcUtil.close(rs);
		}
		
	}
	/*
	 select sys.columns.name, sys.types.name, sys.columns.max_length, sys.columns.is_nullable, 
  (select count(*) from sys.identity_columns where sys.identity_columns.object_id = sys.columns.object_id and sys.columns.column_id = sys.identity_columns.column_id) as is_identity ,
  (select value from sys.extended_properties where sys.extended_properties.major_id = sys.columns.object_id and sys.extended_properties.minor_id = sys.columns.column_id) as description
  from sys.columns, sys.tables, sys.types where sys.columns.object_id = sys.tables.object_id and sys.columns.system_type_id=sys.types.system_type_id and sys.tables.name='student' order by sys.columns.column_id
	 */
	public static CustomResultSet getMssql2005TableStructure(Connection conn,String tableName){
		StringBuilder sql = new StringBuilder("select \n");
		sql.append("sys.columns.name, sys.types.name, sys.columns.max_length, sys.columns.is_nullable,\n");
		sql.append("(select count(*) from sys.identity_columns where sys.identity_columns.object_id = sys.columns.object_id and sys.columns.column_id = sys.identity_columns.column_id) as is_identity ,\n");
		sql.append("(select cast(isnull(value, ' ')as varchar) from sys.extended_properties where sys.extended_properties.major_id = sys.columns.object_id and sys.extended_properties.minor_id = sys.columns.column_id) as description\n");
		sql.append("from sys.columns, sys.tables, sys.types where sys.columns.object_id = sys.tables.object_id and sys.columns.system_type_id=sys.types.system_type_id and sys.tables.name='");
		sql.append(tableName).append("' order by sys.columns.column_id\n");
		
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(sql.toString());
			return new CustomResultSet(rs,false);
		} catch (SQLException e) {
			throw new RuntimeException("executeQuery失败",e);
		}finally{
			JdbcUtil.close(rs);
		}
	}
	public static String[] getAllTableAndView(Connection conn) throws SQLException{
		 //获取数据库元数据  
		List<String> list = new ArrayList<String>();
	    DatabaseMetaData dbmd = conn.getMetaData();

	    String[] types = { "TABLE","VIEW" };
	    ResultSet resultSet = dbmd.getTables(null, null, "%", types);

	    while (resultSet.next()) {
	      String tableName = resultSet.getString(3);
	      list.add(tableName);
	    }
	    return list.toArray(new String[0]);
	}
}
