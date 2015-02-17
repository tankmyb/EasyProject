package ${model.packageName};
import com.easy.persistance.sql.table.Table;
import java.util.Date;
import com.easy.persistance.sql.select.datebase.MysqlSelect;
public class ${model.className} extends MysqlSelect{
   public static final ${model.metadataName} meta = new ${model.metadataName}();
   public ${model.className}(){
     super(new Table(meta.tableName,meta.tableAlias));
     setPrimaryKeyName(meta.primaryKeys);
   }
   public void setPrimaryKey(Integer id){
	   this.addEqualCondition(meta.primaryKeys,id);
   }
  <#list model.fieldList as item>
  public void set${item.javaFieldName?cap_first}(${item.fieldType} ${item.javaFieldName}){
     this.addEqualCondition(meta.F_${item.javaFieldName},${item.javaFieldName});
  }
  </#list>

}