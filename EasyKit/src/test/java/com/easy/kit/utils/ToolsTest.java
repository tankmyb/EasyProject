package com.easy.kit.utils;

import java.math.BigDecimal;

import org.junit.Test;


public class ToolsTest {

	@Test
	public void testIsBlank(){
		Short s = new Short("0");
		boolean isTrue = Tools.isBlank(s);
		System.out.println(isTrue);
		BigDecimal bd = new BigDecimal("0");
		System.out.println(Tools.isBlank(bd));
	}
}
