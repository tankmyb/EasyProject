package com.easy.persistance.dao.markview;

import java.util.List;

import com.easy.persistance.creater.dao.ITableBaseDAO;
import com.easy.persistance.orm.MarkRow;

public interface MarkDAO extends ITableBaseDAO<MarkRow>{

	List<MarkRow> getList(String no);
}
