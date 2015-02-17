package com.easy.kit.utils.string;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testToSplitStrString() {
		String str ="1,2,3,4,";
		String[] array = StringUtil.toSplitStr(str);
		System.out.println(array[3]);
	}
	@Test
	public void testReplace(){
		String templet = "aaaa@{aa}d{2}{sad}";
		Pattern pattern = Pattern.compile("@\\{\\w+\\}");
		Matcher matcher = pattern.matcher(templet);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
		    matcher.appendReplacement(sb, "?");
		}
		matcher.appendTail(sb);
		System.out.println(sb.toString());
	}
}
