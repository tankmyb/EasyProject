package com.easy.persistance.logger;

import com.easy.persistance.common.TimeMeter;

/**
 * 
 *  sql日志输出
 *
 */
public interface ISqlLogger {

	void sql(TimeMeter timeMeter,String sql);

	void  sql(String sql);
}
