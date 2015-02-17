package com.easy.persistance.orm;
import com.easy.persistance.orm.SuperRow;
import java.util.Date;
public class MarkViewRow extends SuperRow{

	public static final long serialVersionUID = 1L;
	public MarkViewRow(){
	}
     public void setCouId(Integer couId){
        setValue("cou_id",couId);
     }
     public Integer getCouId(){
        return (Integer)getValue("cou_id");
     }
     
     public void setCouName(String couName){
        setValue("cou_name",couName);
     }
     public String getCouName(){
        return (String)getValue("cou_name");
     }
     
     public void setCouNo(String couNo){
        setValue("cou_no",couNo);
     }
     public String getCouNo(){
        return (String)getValue("cou_no");
     }
     
     public void setStuId(Integer stuId){
        setValue("stu_id",stuId);
     }
     public Integer getStuId(){
        return (Integer)getValue("stu_id");
     }
     
     public void setStuNo(String stuNo){
        setValue("stu_no",stuNo);
     }
     public String getStuNo(){
        return (String)getValue("stu_no");
     }
     
     public void setStuName(String stuName){
        setValue("stu_name",stuName);
     }
     public String getStuName(){
        return (String)getValue("stu_name");
     }
     
     public void setStuUnit(String stuUnit){
        setValue("stu_unit",stuUnit);
     }
     public String getStuUnit(){
        return (String)getValue("stu_unit");
     }
     
     public void setStuAge(Integer stuAge){
        setValue("stu_age",stuAge);
     }
     public Integer getStuAge(){
        return (Integer)getValue("stu_age");
     }
     
     public void setStuCreatetime(Date stuCreatetime){
        setValue("stu_createTime",stuCreatetime);
     }
     public Date getStuCreatetime(){
        return (Date)getValue("stu_createTime");
     }
     
     public void setId(Integer id){
        setValue("id",id);
     }
     public Integer getId(){
        return (Integer)getValue("id");
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