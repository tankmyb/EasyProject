package com.easy.persistance.orm;
import com.easy.persistance.sql.table.Table;
import java.util.Date;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
public class MarkViewSelect extends MysqlSelect{
   public static final MarkViewMetaData meta = new MarkViewMetaData();
   public MarkViewSelect(){
     super(new Table(meta.tableName,meta.tableAlias));
     setPrimaryKeyName(meta.primaryKeys);
   }
   public void setPrimaryKey(Integer id){
	   this.addEqualCondition(meta.primaryKeys,id);
   }
  public void setCouId(Integer couId){
     this.addEqualCondition(meta.F_couId,couId);
  }
  public void setCouName(String couName){
     this.addEqualCondition(meta.F_couName,couName);
  }
  public void setCouNo(String couNo){
     this.addEqualCondition(meta.F_couNo,couNo);
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
  public void setId(Integer id){
     this.addEqualCondition(meta.F_id,id);
  }
  public void setMaStuNo(String maStuNo){
     this.addEqualCondition(meta.F_maStuNo,maStuNo);
  }
  public void setMaCouNo(String maCouNo){
     this.addEqualCondition(meta.F_maCouNo,maCouNo);
  }
  public void setMaMark(Integer maMark){
     this.addEqualCondition(meta.F_maMark,maMark);
  }

}