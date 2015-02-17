package ${model.packageName};
public class ${model.className} {

  public static final String tableName="${model.tableName}";
  public static final String javaName="${model.javaTableName}";
  public static final String tableAlias="${model.tableAlias}";
  public static final String tableFullName="${model.tableName} ${model.tableAlias}";
  public static final String ALL="*";
  public static final Boolean isAutoIncrement=${model.isAutoIncrement?if_exists};
  public static final String primaryKeys="${model.primaryKeys?if_exists}";
  public static final String pk="${model.pk?if_exists}";
  
  <#list model.fieldList as item>
  public static final String F_${item.javaFieldName} = "${item.tableFieldName}";
  </#list>
}
