package com.easy.persistance.orm;
import com.easy.persistance.orm.SuperRow;
import java.util.Date;
public class CourseRow extends SuperRow{

	public static final long serialVersionUID = 1L;
	public CourseRow(){
	}
     public void setId(Integer id){
        setValue("id",id);
     }
     public Integer getId(){
        return (Integer)getValue("id");
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
     
}