package com.easy.persistance.orm;
import com.easy.persistance.creater.dao.ITableBaseDAO;
import com.easy.persistance.parser.DeleteParser;
import com.easy.persistance.parser.InsertParser;
import com.easy.persistance.parser.UpdateParser;
import com.easy.persistance.parser.DeleteByIdsParser;
import com.easy.persistance.exception.UpdateNoRecordException;
import com.easy.persistance.bean.SaveBean;
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
public class StudentBaseDAO implements ITableBaseDAO<StudentRow>{
  @Resource
  protected IJdbcTemplateEx jdbcTemplateEx;
  
  @Override
  public List<StudentRow> getList(ISelect select){
    String sql = select.getSQL();
    DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(sql,select.getValues());
    if(dbrs.size()>0){
      List<StudentRow> list = new ArrayList<StudentRow>();
      for(Iterator<IRow> it=dbrs.iterator();it.hasNext();){
      StudentRow row = new StudentRow();
      row.load(it.next().getMap());
      list.add(row);
    }
    return list;
    }
    return null;
  }
  
  @Override
  public List<StudentRow> getAll(){
	 StudentSelect select = new StudentSelect();
	 select.addAllColumn();
	 return getList(select);
  }
  
   @Override
   public int count(ISelect select) {
	  return jdbcTemplateEx.count(select.getCountSql(),select.getValues());
   }
   @Override
	public StudentRow get(ISelect select) {
		List<StudentRow> list = getList(select);
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
		List<StudentRow> list = this.getList(select);
		if(list == null){
			list = new ArrayList<StudentRow>();
		}
		int totalRecord = this.count(select);
		return new Page(totalRecord,list);
   }
    @Override
	public StudentRow get(Integer id) {
		StudentSelect select = new StudentSelect();
		select.addAllColumn();
		select.setPrimaryKey(id);
		List<StudentRow> list = getList(select);
		return list == null ? null : list.get(0);
	}
	@Override
  public void insert(StudentRow row){
    InsertParser parser = new InsertParser(getTableName(),row.getRecord());
    jdbcTemplateEx.insert(parser.getSQL(),parser.getValues());
  }
	@Override
	public int update(StudentRow row) {
	    String primaryKeyName = getPrimaryKeyName();
	    Integer id = (Integer)row.getRecord().get(primaryKeyName);
	    StudentRow oldRow = get(id);
		Map<String,Object> updateRecord = oldRow.getUpdateRecord(row);
		try{
		   UpdateParser parser = new UpdateParser(getTableName(),primaryKeyName,id,updateRecord);
		   return jdbcTemplateEx.update(parser.getSQL(),parser.getValues());
		}catch(UpdateNoRecordException e){
		   return -2;
		}
	}
	@Override
	public SaveBean save(StudentRow newRow){
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
	public int delete(StudentRow row) {
		DeleteParser parser = new DeleteParser(getTableName(),row.getRecord());
		return jdbcTemplateEx.delete(parser.getSQL(),parser.getValues());
	}
	@Override
	public int delete(String ids) {
	    DeleteByIdsParser parser = new DeleteByIdsParser(this.getTableName(),this.getPrimaryKeyName(), ids);
		return jdbcTemplateEx.delete(parser.getSQL(),parser.getValues());
	}
	
	@Override
  public int insertWithGeneratedKey(StudentRow row){
    InsertParser parser = new InsertParser(getTableName(),row.getRecord());
    return jdbcTemplateEx.insertWithGeneratedKey(parser.getSQL(),parser.getValues());
  }
   public String getPrimaryKeyName(){
	  return "stu_id";
  }
  public String getTableName(){
	  return "student";
  }
 
}
