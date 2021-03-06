package com.easyjsp.util;

/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MyEclipse Struts Creation date: 06-28-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action validate="true"
 */
public class LoginDeal {
	/*
	 * Generated Methods
	 */

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public boolean execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RSAPublicKey rsap = (RSAPublicKey) RSAUtil.getKeyPair().getPublic();
		String module = rsap.getModulus().toString(16);
		String empoent = rsap.getPublicExponent().toString(16);
		System.out.println("module");
		System.out.println(module);
		System.out.println("empoent");
		System.out.println(empoent);
		request.setAttribute("m", module);
		request.setAttribute("e", empoent);
		return true;
	}
}