package ${model.packageName};
<#if (model.primaryKeyName ? exists)>
import com.easy.persistance.creater.dao.ITableBaseDAO;
import com.easy.persistance.parser.DeleteParser;
import com.easy.persistance.parser.InsertParser;
import com.easy.persistance.parser.UpdateParser;
import com.easy.persistance.parser.DeleteByIdsParser;
import com.easy.persistance.exception.UpdateNoRecordException;
import com.easy.persistance.bean.SaveBean;
<#else>
import com.easy.persistance.creater.dao.IViewBaseDAO;
</#if>
import com.easy.persistance.resultset.IRow;
import com.easy.persistance.resultset.DBResultSet;
import javax.annotation.Resource;
import com.easy.persistance.dao.IJdbcTemplateEx;
import com.easy.persistance.sql.select.ISelect;
import com.easy.persistance.page.ext.Page;
import com.easy.persistance.page.ext.PageQuery;
import com.easy.persistance.sql.select.ASelect;
import com.easy.persistance.util.ColumnFormatUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
public class ${model.className} implements <#if (model.primaryKeyName ? exists)>ITableBaseDAO<#else>IViewBaseDAO</#if><${model.rowName}>{
  @Resource
  protected IJdbcTemplateEx jdbcTemplateEx;
  
  @Override
  public List<${model.rowName}> getList(ISelect select){
    String sql = select.getSQL();
    DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(sql,select.getValues());
    if(dbrs.size()>0){
      List<${model.rowName}> list = new ArrayList<${model.rowName}>();
      for(Iterator<IRow> it=dbrs.iterator();it.hasNext();){
      ${model.rowName} row = new ${model.rowName}();
      row.load(it.next().getMap());
      list.add(row);
    }
    return list;
    }
    return null;
  }
  
  @Override
  public List<${model.rowName}> getAll(){
	 ${model.selectName} select = new ${model.selectName}();
	 select.addAllColumn();
	 return getList(select);
  }
  
   @Override
   public int count(ISelect select) {
	  return jdbcTemplateEx.count(select.getCountSql(),select.getValues());
   }
   @Override
	public ${model.rowName} get(ISelect select) {
		List<${model.rowName}> list = getList(select);
		return list == null ? null : list.get(0);
	}
   @Override
   public Page getExtPage(ASelect select,PageQuery pageQuery) {
      String sortField = null;
		if(StringUtils.isNotBlank(pageQuery.getSort())){
			sortField = ColumnFormatUtil.underLineName(pageQuery.getSort());
		}
		if(StringUtils.isNotBlank(pageQuery.getDir())){
			if(pageQuery.getDir().equals("DESC")){
				select.addOrderBys(true, sortField);
			}else {
				select.addOrderBys(sortField);
			}
		}
		select.setLimit(pageQuery.getLimit());
		select.setOffset(pageQuery.getStart());
		List<${model.rowName}> list = this.getList(select);
		if(list == null){
			list = new ArrayList<${model.rowName}>();
		}
		int totalRecord = this.count(select);
		return new Page(totalRecord,list);
   }
   <#if (model.primaryKeyName ? exists)>
    @Override
	public ${model.rowName} get(Integer id) {
		${model.selectName} select = new ${model.selectName}();
		select.addAllColumn();
		select.setPrimaryKey(id);
		List<${model.rowName}> list = getList(select);
		return list == null ? null : list.get(0);
	}
	@Override
  public void insert(${model.rowName} row){
    InsertParser parser = new InsertParser(getTableName(),row.getRecord());
    jdbcTemplateEx.insert(parser.getSQL(),parser.getValues());
  }
	@Override
	public int update(${model.rowName} row) {
	    String primaryKeyName = getPrimaryKeyName();
	    Integer id = (Integer)row.getRecord().get(primaryKeyName);
	    ${model.rowName} oldRow = get(id);
		Map<String,Object> updateRecord = oldRow.getUpdateRecord(row);
		try{
		   UpdateParser parser = new UpdateParser(getTableName(),primaryKeyName,id,updateRecord);
		   return jdbcTemplateEx.update(parser.getSQL(),parser.getValues());
		}catch(UpdateNoRecordException e){
		   return -2;
		}
	}
	@Override
	public SaveBean save(${model.rowName} newRow){
	   String primaryKeyName = getPrimaryKeyName();
	   Integer id = (Integer)newRow.getRecord().get(primaryKeyName);
	   SaveBean saveBean = new SaveBean();
	   if(id==null){
			insert(newRow);
		}else {
			int flag = update(newRow);
			if(flag ==2){
			   saveBean.setNotUpdateRecord(true);
			}
		}
	   return saveBean;
	}
	@Override
	public int delete(${model.rowName} row) {
		DeleteParser parser = new DeleteParser(getTableName(),row.getRecord());
		return jdbcTemplateEx.delete(parser.getSQL(),parser.getValues());
	}
	@Override
	public int delete(String ids) {
	    DeleteByIdsParser parser = new DeleteByIdsParser(this.getTableName(),this.getPrimaryKeyName(), ids);
		return jdbcTemplateEx.delete(parser.getSQL(),parser.getValues());
	}
	
	@Override
  public int insertWithGeneratedKey(${model.rowName} row){
    InsertParser parser = new InsertParser(getTableName(),row.getRecord());
    return jdbcTemplateEx.insertWithGeneratedKey(parser.getSQL(),parser.getValues());
  }
   public String getPrimaryKeyName(){
	  return "${model.primaryKeyName?if_exists}";
  }
  </#if>
  public String getTableName(){
	  return "${model.tableName}";
  }
 
}
