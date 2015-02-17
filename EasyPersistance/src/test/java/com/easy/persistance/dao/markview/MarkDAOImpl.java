package com.easy.persistance.dao.markview;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easy.persistance.orm.MarkBaseDAO;
import com.easy.persistance.orm.MarkRow;
import com.easy.persistance.orm.MarkSelect;

@Repository
public class MarkDAOImpl extends MarkBaseDAO implements MarkDAO{

	@Override
	public List<MarkRow> getList(String no) {
		MarkSelect ms = new MarkSelect();
		ms.setMaCouNo(no);
		return getList(ms);
	}

}
