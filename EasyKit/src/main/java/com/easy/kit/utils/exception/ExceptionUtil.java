package com.easy.kit.utils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.easy.kit.utils.log.ExceptionLogger;

public class ExceptionUtil {


	public static String getDetailMessage(Throwable e) {
		StringWriter writer = new StringWriter();
		try {
			e.printStackTrace(new PrintWriter(writer));
			return writer.getBuffer().toString();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (Exception ex) {
					ExceptionLogger.exception(ex);
				}
		}
	}
}
