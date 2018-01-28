package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Iterator;

public class YQTokenizer {
	String myString = "";

	String mySeperator = "";

	ArrayList myArray = new ArrayList();

	Iterator iter = null;

	public YQTokenizer(String cont, String seperator) {
		// 如果字符串以分隔符开头则先去掉分隔符
		while (cont.indexOf(seperator) == 0) {
			cont = cont.substring(seperator.length());
		}
		this.myString = cont;
		this.mySeperator = seperator;
		init();
	}

	private void init() {
		String aa = this.myString;
		int j = 0;
		int m = -1;
		while ((m = aa.indexOf(this.mySeperator)) != -1) {
			this.myArray.add(j++, aa.substring(0, m));
			aa = aa.substring(m + this.mySeperator.length());
		}
		if (aa.trim().length() > 0) {
			this.myArray.add(j, aa);
		}
		iter = this.myArray.iterator();
	}

	public int countTokens() {
		return this.myArray.size();
	}

	public String nextToken() {
		return (String) iter.next();
	}
	public boolean hasMoreTokens()
	{
		return this.iter.hasNext();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
