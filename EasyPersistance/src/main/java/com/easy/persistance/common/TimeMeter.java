package com.easy.persistance.common;

import java.util.Date;

import com.easy.kit.utils.datetime.DateTimeUtil;
import com.easy.kit.utils.string.StringUtil;

public class TimeMeter {
	private Date begin;
	private Date end;

	public TimeMeter() {
		begin =new Date();
	}

	public TimeMeter(Date begin) {
		this.begin = begin;
	}


	public Date getBegin() {
		return this.begin;
	}

	public TimeMeter setEnd(Date end) {
		this.end = end;
		return this;
	}

	public Date getEnd() {
		return this.end;
	}

	public TimeMeter end() {
		this.end = new Date();
		return this;
	}

	public long getElapse() {
		return (end == null ? new Date() : end).getTime() - begin.getTime();
	}

	@Override
	public String toString() {
		return getElapse("elapsed:{0}");
	}

	public String getElapse(String pattern) {
		return StringUtil.format(pattern, DateTimeUtil
				.formatElapse(getElapse()));
	}
}
