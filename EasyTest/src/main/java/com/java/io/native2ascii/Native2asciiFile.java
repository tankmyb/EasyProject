package com.java.io.native2ascii;

import java.io.IOException;

public class Native2asciiFile {

	private static final String java_path = "D://Program Files//Java//jdk1.6.0_30";
	private static final String target_file = "D://boss//src//com//chinamobile//gd//boss//ReadXmlResult.java";
	private static final String result_file = "D://boss//src//com//chinamobile//gd//boss//ReadXmlResult1.java";
	private static final String encoding = "utf-8";

	public static void native2ascii() {
		try {
			System.out.println("Begin to execute...");
			Runtime.getRuntime().exec(
					java_path + "//bin//native2ascii.exe -reverse "  + target_file + " " + result_file);
			System.out.println("End");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String arg[]) {
		native2ascii();
	}
}
