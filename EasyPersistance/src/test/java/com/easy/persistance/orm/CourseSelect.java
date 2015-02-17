package com.easy.persistance.orm;
import com.easy.persistance.sql.table.Table;
import java.util.Date;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
public class CourseSelect extends MysqlSelect{
   public static final CourseMetaData meta = new CourseMetaData();
   public CourseSelect(){
     super(new Table(meta.tableName,meta.tableAlias));
     setPrimaryKeyName(meta.primaryKeys);
   }
   public void setPrimaryKey(Integer id){
	   this.addEqualCondition(meta.primaryKeys,id);
   }
  public void setId(Integer id){
     this.addEqualCondition(meta.F_id,id);
  }
  public void setCouName(String couName){
     this.addEqualCondition(meta.F_couName,couName);
  }
  public void setCouNo(String couNo){
     this.addEqualCondition(meta.F_couNo,couNo);
  }

}