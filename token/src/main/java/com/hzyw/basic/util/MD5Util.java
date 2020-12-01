package com.hzyw.basic.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {

	protected MD5Util(){

	}

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String password) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(password), HASH_ITERATIONS).toHex();
	}

	public static String encrypt(String username, String password) {
		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username.toLowerCase() + password),
				HASH_ITERATIONS).toHex();
	}

	public static void main(String[] args) {
		System.out.println(encrypt("HZAdmin", "123"));
//		System.out.println(encrypt("zhangs","123456"));
		/*System.out.println(encrypt("1234qwer"));
		System.out.println(Base64.decodeToString("19383202f670593628922c18416c55fb"));
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); // 设置key长度
		// 生成key
		Key key = aesCipherService.generateNewKey();
		String text2 = new String(aesCipherService
				.decrypt(Hex.decode("d0d39e180b191b76ac513cf0502fd7ea"), key.getEncoded()).getBytes());
		System.out.println(text2+"===");*/
	}

}
