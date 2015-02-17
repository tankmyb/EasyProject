package com.easy.persistance.orm;
import com.easy.persistance.orm.SuperRow;
import java.util.Date;
public class StudentRow extends SuperRow{

	public static final long serialVersionUID = 1L;
	public StudentRow(){
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
     
}