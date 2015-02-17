package com.easy.persistance.orm;
import com.easy.persistance.sql.table.Table;
import java.util.Date;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
public class StudentSelect extends MysqlSelect{
   public static final StudentMetaData meta = new StudentMetaData();
   public StudentSelect(){
     super(new Table(meta.tableName,meta.tableAlias));
     setPrimaryKeyName(meta.primaryKeys);
   }
   public void setPrimaryKey(Integer id){
	   this.addEqualCondition(meta.primaryKeys,id);
   }
  public void setStuId(Integer stuId){
     this.addEqualCondition(meta.F_stuId,stuId);
  }
  public void setStuNo(String stuNo){
     this.addEqualCondition(meta.F_stuNo,stuNo);
  }
  public void setStuName(String stuName){
     this.addEqualCondition(meta.F_stuName,stuName);
  }
  public void setStuUnit(String stuUnit){
     this.addEqualCondition(meta.F_stuUnit,stuUnit);
  }
  public void setStuAge(Integer stuAge){
     this.addEqualCondition(meta.F_stuAge,stuAge);
  }
  public void setStuCreatetime(Date stuCreatetime){
     this.addEqualCondition(meta.F_stuCreatetime,stuCreatetime);
  }

}