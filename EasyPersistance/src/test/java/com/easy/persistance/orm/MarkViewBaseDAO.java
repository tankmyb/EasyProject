package com.easy.persistance.orm;
import com.easy.persistance.creater.dao.IViewBaseDAO;
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
public class MarkViewBaseDAO implements IViewBaseDAO<MarkViewRow>{
  @Resource
  protected IJdbcTemplateEx jdbcTemplateEx;
  
  @Override
  public List<MarkViewRow> getList(ISelect select){
    String sql = select.getSQL();
    DBResultSet dbrs = jdbcTemplateEx.queryForDBResultSet(sql,select.getValues());
    if(dbrs.size()>0){
      List<MarkViewRow> list = new ArrayList<MarkViewRow>();
      for(Iterator<IRow> it=dbrs.iterator();it.hasNext();){
      MarkViewRow row = new MarkViewRow();
      row.load(it.next().getMap());
      list.add(row);
    }
    return list;
    }
    return null;
  }
  
  @Override
  public List<MarkViewRow> getAll(){
	 MarkViewSelect select = new MarkViewSelect();
	 select.addAllColumn();
	 return getList(select);
  }
  
   @Override
   public int count(ISelect select) {
	  return jdbcTemplateEx.count(select.getCountSql(),select.getValues());
   }
   @Override
	public MarkViewRow get(ISelect select) {
		List<MarkViewRow> list = getList(select);
		return list == null ? null : list.get(0);
	}
   @Override
   public Page getExtPage(ASelect select,PageQuery pageQuery) {
      String sortField = null;
		if(pageQuery.getSort().equals("pk")){
			sortField = select.getPrimaryKeyName();
		}else {
			sortField = ColumnFormatUtil.underLineName(pageQuery.getSort());
		}
		if(pageQuery.getDir().equals("DESC")){
			select.addOrderBys(true, sortField);
		}else {
			select.addOrderBys(sortField);
		}
		select.setLimit(pageQuery.getLimit());
		select.setOffset(pageQuery.getStart());
		List<MarkViewRow> list = this.getList(select);
		if(list == null){
			list = new ArrayList<MarkViewRow>();
		}
		int totalRecord = this.count(select);
		return new Page(totalRecord,list);
   }
  public String getTableName(){
	  return "mark_view";
  }
 
}
