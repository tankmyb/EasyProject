package com.easy.persistance.orm;
import com.easy.persistance.orm.SuperRow;
import java.util.Date;
public class MarkRow extends SuperRow{

	public static final long serialVersionUID = 1L;
	public MarkRow(){
	}
     public void setMaId(Integer maId){
        setValue("ma_id",maId);
     }
     public Integer getMaId(){
        return (Integer)getValue("ma_id");
     }
     
     public void setMaStuNo(String maStuNo){
        setValue("ma_stu_no",maStuNo);
     }
     public String getMaStuNo(){
        return (String)getValue("ma_stu_no");
     }
     
     public void setMaCouNo(String maCouNo){
        setValue("ma_cou_no",maCouNo);
     }
     public String getMaCouNo(){
        return (String)getValue("ma_cou_no");
     }
     
     public void setMaMark(Integer maMark){
        setValue("ma_mark",maMark);
     }
     public Integer getMaMark(){
        return (Integer)getValue("ma_mark");
     }
     
}