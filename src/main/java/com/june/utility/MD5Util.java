package com.june.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5验证工具
 * 
 * @author victor
 */
public class MD5Util {
	/**
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e','f' };
	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对字符串md5
	 * @param str
	 * @return
	 * @date 2016年6月28日 下午1:25:17
	 * @writer wjw.happy.love@163.com
	 */
	public static String getMD5(String str){
		byte[] buffer = str.getBytes();
		// 使用指定的字节更新摘要
		messagedigest.update(buffer);
		// 获得密文
        byte[] md = messagedigest.digest();
		return bufferToHex(md);
	}
	
	public static String smallFileMD5(File smallFile) throws IOException {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(smallFile);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, smallFile.length());
			// MessageDigest md5 = MessageDigest.getInstance("MD5");
			messagedigest.update(byteBuffer);
			BigInteger bi = new BigInteger(1, messagedigest.digest());
			value = bi.toString(16);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != in) {
				in.close();
			}
		}

		return value;
	}
	
	/**
	 * 获取文件的md5
	 * @deprecated 此方法未测试，请参考apache的方法org.apache.commons.codec.digest.DigestUtils
	 */
	public static String getFileMD5String(InputStream fis) throws IOException {

		byte[] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int k, int j) {
		char[] str = new char[j*2];
		for(int i=0;i<j;i++){
			byte bt = bytes[i];
			str[k++] = hexDigits[bt>>>4&0xf];
			str[k++] = hexDigits[bt&0xf];
		}
		return new String(str);
	}


	public static void main(String[] args) throws Exception {
		//MultipartFile myfile = null;
		//InputStream inputStream = FileUpLoadDownload.getFileStream(myfile);
		//String md5 = getFileMD5String(inputStream);
		//System.out.println("md5:" + md5);
		System.out.println(MD5Util.getMD5("a"));//d41d8cd98f00b204e9800998ecf8427e
		System.out.println(MD5Util.getMD5("b"));//d41d8cd98f00b204e9800998ecf8427e

		System.out.println(MD5Util.getMD5("wjw.happy.love@163.com"));
	}
}
