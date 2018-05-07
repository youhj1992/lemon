package org.lemon.yhj.cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DES {
	private static Logger log = LoggerFactory.getLogger(DES.class);

	private DES() {
	}

	/**
	 * DES算法
	 */
	private static final String DES_ALGORITHM = "DES/ECB/PKCS5Padding";

	public static String encryt(String text, String key) {
		SecureRandom sr = new SecureRandom();
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey deskey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, deskey, sr);
			return Base64.encodeBase64String(cipher.doFinal(text.getBytes("utf-8")));
		} catch (Exception e) {
			log.error("DES 加密失败");
			return null;
		}
	}
	
	public static String getDesKey(){
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);// 设置密钥的长度为56位
			// 生成一个Key
			SecretKey generateKey = keyGenerator.generateKey();
			
			return Hex.encodeHexString(generateKey.getEncoded());
		} catch (Exception e) {
			log.error("DES key 生成失败");
			return null;
		}
	}

}
