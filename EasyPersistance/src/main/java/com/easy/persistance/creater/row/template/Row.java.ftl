package ${model.packageName};
import com.easy.persistance.orm.SuperRow;
import java.util.Date;
public class ${model.className} extends SuperRow{

	public static final long serialVersionUID = 1L;
	public ${model.className}(){
	}
	<#list model.fieldList as item>
     public void set${item.javaFieldName?cap_first}(${item.fieldType} ${item.javaFieldName}){
        setValue("${item.tableFieldName}",${item.javaFieldName});
     }
     public ${item.fieldType} get${item.javaFieldName?cap_first}(){
        return (${item.fieldType})getValue("${item.tableFieldName}");
     }
     
  </#list>  
}