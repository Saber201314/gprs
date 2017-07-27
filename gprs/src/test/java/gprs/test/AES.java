/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gprs.test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * AES
 * @author dengyuhan
 * @create 2016/3/31 15:43
 */
public class AES {
    // /** 算法/模式/填充 **/
    private static final String CipherMode = "AES/ECB/PKCS5Padding";
    // private static final String CipherMode = "AES";

    /**
     * 生成一个AES密钥对象
     * @return
     */
    public static SecretKeySpec generateKey(){
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
	        kgen.init(128, new SecureRandom());
	        SecretKey secretKey = kgen.generateKey();  
	        byte[] enCodeFormat = secretKey.getEncoded();  
	        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
    }

    /**
     * 生成一个AES密钥字符串
     * @return
     */
    public static String generateKeyString(){
    	return byte2hex(generateKey().getEncoded());
    }

    /**
     * 加密字节数据
     * @param content
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过byte[]类型的密钥加密String
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
            String result = byte2hex(data);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥加密String
     * @param content
     * @param key
     * @return 16进制密文字符串
     */
    public static String encrypt(String content,String key) {
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt(data,new SecretKeySpec(hex2byte(key), "AES").getEncoded());
        String result = byte2hex(data);
        return result;
    }

    /**
     * 通过byte[]类型的密钥解密byte[]
     * @param content
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] content,byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过String类型的密钥 解密String类型的密文
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt(data, hex2byte(key));
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过byte[]类型的密钥 解密String类型的密文
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content,byte[] key) {
    	try {
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key, "AES"));
            byte[] data = cipher.doFinal(hex2byte(content));
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数组转成16进制字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuffer sb = new StringBuffer(b.length * 2);
        String tmp;
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    /**
     * 将hex字符串转换成字节数组
     * @param inputString
     * @return
     */
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }
    
    public static void main(String[] args) {
    	try{
	    	// 加载RSA
			//RSAPublicKey publicKey = RSA.loadPublicKey(new FileInputStream("E:\\2016\\9\\19\\cert\\apiclient_cert.p12"));
			//RSAPrivateKey privateKey = RSA.loadPrivateKey(new FileInputStream("E:\\2016\\9\\19\\cert\\apiclient_cert.p12"));
		
			// 生成AES密钥
			 String key=AES.generateKeyString();
			 //    			String key = "1234567890123456";
			System.out.println("AES-KEY:" + key);
		
			// 内容
//			String content = "{\"mobile\":\"18660803982\",\"amount\":\"100\",\"companyId\":\"8\",\"orderNum\":\"123456789\",\"bussissId\":\"18154568754\",\"notifyUrl\":\"\"}";
			String content = "{\"mobile\":\"15005414383\",\"amount\":\"10\",\"companyId\":\"8\",\"orderNum\":\"123456789\",\"notifyUrl\":\"\",\"bussissId\":\"1101\"}";
			
			// 用RSA公钥加密AES-KEY
			//String miKey = RSA.encryptByPublicKey(key, publicKey);
			//System.out.println("加密后的AES-KEY:" + miKey);
			//System.out.println("加密后的AES-KEY长度:" + miKey.length());
		
			// 用RSA私钥解密AES-KEY
			//String mingKey = RSA.decryptByPrivateKey(miKey, privateKey);
			//System.out.println("解密后的AES-KEY:" + mingKey);
			//System.out.println("解密后的AES-KEY长度:" + mingKey.length());
		
			// 用AES加密内容
			//String miContent = AES.encrypt(content, mingKey);
            String miContent = AES.encrypt(content, "6369BE91F580F990015D709D4D69FA41");            
			System.out.println("AES加密后的内容:" + miContent);		
			// 用AES解密内容
			String a="f540b4c7424aab0ad6b7758cd70bd35e7debdd9685d3340d218dd364c73788b6bfeca2a53d4f1c454d0236ad07eedba720ca712da4d35e55fbed2aacbc70e51208a328937a8be1890d3908cc5490b61018b579f22492f4a2eda89328092f6c432b42f5bcd3e59824464d0ef43791a275";
			String mingContent = AES.decrypt(a, "CB5141F4C6AC6A7D47607E8210CEA9B1");
			System.out.println("AES解密后的内容:" + mingContent);		
    	}catch (Exception e) {
    		e.printStackTrace();
			// TODO: handle exception
		}

	}
}