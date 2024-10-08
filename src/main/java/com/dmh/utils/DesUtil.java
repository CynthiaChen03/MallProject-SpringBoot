package com.dmh.utils;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;


public class DesUtil {
	private final static String DES = "DES";

	public static void main(String[] args) throws Exception {
		String data = "123 456";
		String key = "wow!@#$%";
		System.err.println(encrypt(data, key));
		System.err.println(decrypt(encrypt(data, key), key));

	}


	public static String encrypt(String data, String key) throws Exception {

//		byte[] bt = encrypt(data.getBytes(), key.getBytes());
//		String strs = new BASE64Encoder().encode(bt);
		Base64.Encoder encoder = Base64.getEncoder();


		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = encoder.encodeToString(bt);
		return strs;
	}


	public static String decrypt(String data, String key) throws IOException,
	Exception {
		if (data == null)
			return null;
		//BASE64Decoder decoder = new BASE64Decoder();
		Base64.Decoder decoder = Base64.getDecoder();
		//byte[] buf = decoder.decodeBuffer(data);
		byte[] buf = decoder.decode(data);
		byte[] bt = decrypt(buf,key.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * @param data
	 * @param key  加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}



	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
