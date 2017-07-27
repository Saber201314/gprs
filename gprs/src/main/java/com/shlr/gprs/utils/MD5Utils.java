package com.shlr.gprs.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

import org.apache.commons.codec.digest.DigestUtils;

import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.crypto.digest.DigestAlgorithm;
import com.xiaoleilu.hutool.crypto.digest.DigestUtil;
import com.xiaoleilu.hutool.crypto.digest.Digester;
import com.xiaoleilu.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.xiaoleilu.hutool.crypto.symmetric.SymmetricCrypto;
import com.xiaoleilu.hutool.util.CharsetUtil;
import com.xiaoleilu.hutool.util.HexUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import junit.framework.Assert;

/**
 * @author xucong
 * @version 创建时间：2017年4月9日 上午11:41:19
 * 
 */
public class MD5Utils {
	// 静态方法，便于作为工具类
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getBase32MD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
	public static void main(String[] args) {
		String sign = getMd5("account=pl_fs&mobile=15220002311&package=10&key=c2762809416446c0a23e20b64529cba3");
		System.out.println(sign);
		String sign1 = getBase32MD5Str("123");
		System.out.println(sign1);
		String md5Hex = DigestUtil.md5Hex("account=ps_fs&mobile=15220002311&package=10&key=c2762809416446c0a23e20b64529cba3");
		System.out.println(md5Hex);
		
//		String content = "test中文";
//
//		//随机生成密钥
//		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
//		
//		 SecretKey generateKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
//		 String encodeHexStr = HexUtil.encodeHexStr(key);
//		 System.out.println(encodeHexStr);
//		System.out.println(HexUtil.encodeHexStr(HexUtil.decodeHex(encodeHexStr)));
//		
//		//构建
//		SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, "4165432163131341".getBytes());
//		//加密
//		byte[] encrypt = aes.encrypt(content);
//		//解密
//		byte[] decrypt = aes.decrypt(encrypt);
//
////		Assert.assertEquals(content, StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
//		System.out.println(content+"--------"+StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
//
//		//加密为16进制表示
//		String encryptHex = aes.encryptHex(content);
//		//解密为字符串
//		String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
//		System.out.println(content+"------"+decryptStr);
	}
}
