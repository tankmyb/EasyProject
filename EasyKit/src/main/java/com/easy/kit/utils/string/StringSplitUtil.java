package com.easy.kit.utils.string;

import com.easy.kit.utils.Tools;

public class StringSplitUtil {

	private String splitSign;// 分隔符
	private String endSign;

	private StringBuilder sb = new StringBuilder();

	public StringSplitUtil() {
		splitSign = ",";
	}

	public StringSplitUtil(String sign) {
			splitSign = sign; 
	}

	public StringSplitUtil(String sign, String endSign) {
		splitSign = sign;
		this.endSign = endSign;
	}

	public StringSplitUtil append(Object str) {
		return append(str, true);
	}

	public StringSplitUtil append(Object str, boolean isAddSplitSign) {
		sb.append(str);
		if (isAddSplitSign) {
			sb.append(this.splitSign);
		}
		return this;
	}

	public StringSplitUtil append(Object... str) {
		for (Object s : str) {
			sb.append(s);
		}
		sb.append(this.splitSign);
		return this;
	}

	public String toString() {
		if (sb.length() > 1) {
			if (Tools.isValid(this.endSign)) {
				return (sb.substring(0, sb.length() - splitSign.length()) + endSign);
			} else {
				return (sb.substring(0, sb.length() - splitSign.length())).trim();
			}
		} else {
			return null;
		}
	}

	public void clear() {
		sb = null;
	}

	public boolean isEmpty() {
		return sb.length() == 0;
	}

}
