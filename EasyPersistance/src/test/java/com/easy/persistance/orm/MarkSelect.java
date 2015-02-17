package com.easy.persistance.orm;
import com.easy.persistance.sql.table.Table;
import java.util.Date;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
public class MarkSelect extends MysqlSelect{
   public static final MarkMetaData meta = new MarkMetaData();
   public MarkSelect(){
     super(new Table(meta.tableName,meta.tableAlias));
     setPrimaryKeyName(meta.primaryKeys);
   }
   public void setPrimaryKey(Integer id){
	   this.addEqualCondition(meta.primaryKeys,id);
   }
  public void setMaId(Integer maId){
     this.addEqualCondition(meta.F_maId,maId);
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