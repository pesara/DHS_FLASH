package com.inalab.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class EncryptDecryptUtil {

	private static final Log log = LogFactory.getLog(EncryptDecryptUtil.class);
	private static Logger logger = Logger.getLogger(EncryptDecryptUtil.class);

	private static final String key1 = "I-SOCIAL";
	private static final String key2 = "APR-2012";

	private static byte[] desKeyData1;
	private static byte[] desKeyData2;

	private static DESKeySpec desKeySpec;
	private static IvParameterSpec ivParamSpec;

	private static Cipher encryptCipher;
	private static Cipher decryptCipher;

	private static SecretKeyFactory keyFactory;
	private static SecretKey sKey;

	private static final char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	static {
		init();
	}

	private static void init() {
		logger.info("EncryptDecryptUtil64 init");
		try {

			desKeyData1 = key1.getBytes();
			desKeyData2 = key2.getBytes();
			desKeySpec = new DESKeySpec(desKeyData1);
			ivParamSpec = new IvParameterSpec(desKeyData2);

			keyFactory = SecretKeyFactory.getInstance("DES");
			sKey = keyFactory.generateSecret(desKeySpec);

			encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, sKey, ivParamSpec);

			decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, sKey, ivParamSpec);

		} catch (Exception ex) {
			logger.debug(ex);
		}

	}

	public static String encrypt(String txt) throws Exception {
		byte[] txtData = txt.getBytes();
		byte[] encryptData = encryptCipher.doFinal(txtData);
		StringBuffer sb = new StringBuffer("");

		for (int i = 0; i < encryptData.length; i++) {
			byte2hex(encryptData[i], sb);
		}

		return (sb.toString());
	}

	public static String encrypt64(String txt) throws Exception {
		byte[] txtData = txt.getBytes();
		byte[] encryptData = encryptCipher.doFinal(txtData);
		String str = new String(Base64.encodeBase64(encryptData));

		return (str);
	}

	public static String decrypt(String txt) throws Exception {
		byte[] txtData = hexTobyte(txt);
		byte[] decryptData = decryptCipher.doFinal(txtData);

		return new String(decryptData);
	}

	public static String decrypt64(String txt) throws Exception {
		byte[] txtData = Base64.decodeBase64(txt);
		byte[] decryptData = decryptCipher.doFinal(txtData);

		return new String(decryptData);
	}

	/**
	 * byte2hex
	 * 
	 * @param byte
	 * @param StringBuffer
	 * @return void
	 */
	private static void byte2hex(byte b, StringBuffer buf) {
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);

		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	/**
	 * hexTobyte
	 * 
	 * @param String
	 * @return byte[]
	 */

	private static byte[] hexTobyte(String hexString) {
		int index = 0;
		String hexVal;
		int strLen = hexString.length() / 2;
		byte[] bytes = new byte[strLen];

		for (int i = 0; i < strLen; i++) {
			// Parse 2 char at a time from String and construct hexString
			hexVal = "0x" + hexString.substring(index, index + 2);
			bytes[i] = (byte) (Integer.decode(hexVal)).intValue();
			index += 2;
		}

		return bytes;
	}

	public static void main(String a[]) throws Exception {

		String enc = EncryptDecryptUtil.encrypt64("ranjan@email.com,63,2");
		log.debug("enc=" + enc);
		String dec = EncryptDecryptUtil.decrypt64(enc);
		log.debug("dec=" + dec);
	}

}
