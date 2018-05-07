package org.lemon.yhj.cipher;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** 
 * ClassName:MD5.java <br/>
 * @author  gey 
 * @date 创建时间：2016年11月18日 上午10:36:09 
 * @version 1.0 
 * @parameter  
 * @since   JDK 1.6
 */
public class MD5 {

	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6","7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 
	 * Creates a new instance of MD5.
	 *
	 */
	private MD5() {
		super();
	}

	/**
	 * 
	 * md5:(这里用一句话描述这个方法的作用). <br/>
	 * @param b
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @since JDK 1.6
	 */
	public static String md5(byte[] b) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(b, 0, b.length);
		return byteArrayToHexString(md5.digest());
	}

	/**
	 * 
	 * md5:(这里用一句话描述这个方法的作用). <br/>
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @since JDK 1.6
	 */
	public static String md5(String data) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] b = data.getBytes("UTF8");
		md5.update(b, 0, b.length);
		return byteArrayToHexString(md5.digest());
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++)
			sb.append(byteToHexString(b[i]));

		return sb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

}
