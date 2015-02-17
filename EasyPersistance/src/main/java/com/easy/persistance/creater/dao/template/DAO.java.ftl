package ${model.packageName};
import ${model.ormPackageName}.${model.rowName};
<#if (model.primaryKeyName ? exists)>
import com.easy.persistance.creater.dao.ITableBaseDAO;
public interface ${model.className} extends ITableBaseDAO<${model.rowName}>{
<#else>
import com.easy.persistance.creater.dao.IViewBaseDAO;
public interface ${model.className} extends IViewBaseDAO<${model.rowName}>{
</#if>

}
