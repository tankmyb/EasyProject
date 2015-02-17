package com.easy.persistance.resultset;

import java.util.Iterator;
import java.util.List;

public interface IResultSet {

	public int size();
	public int columnSize();
	public List<IRow> getRows();
	public IRow get(int index);
	public Iterator<IRow> iterator();
}
