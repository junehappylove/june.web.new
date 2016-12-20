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

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5验证工具
 * 
 * @author victor
 */
public class MD5Util {

	protected static Logger logger = LoggerFactory.getLogger(MD5Util.class);
	
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
	
	/**
	 * 小文件的md5计算
	 * @param smallFile
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:17:22
	 * @writer junehappylove
	 */
	public static String smallFileMD5(File smallFile) throws IOException {
		String value = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(smallFile);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, smallFile.length());
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
	 * 小文件的md5计算
	 * @param smallFileName
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:40:49
	 * @writer junehappylove
	 */
	public static String smallFileMD5(String smallFileName) throws IOException {
		//File smallFile = new File(smallFileName);
		return smallFileMD5(new File(smallFileName));
	}
	
	
	/**
	 * 获取大文的MD5计算值
	 * @param bigFileName
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:27:09
	 * @writer junehappylove
	 */
	public static String bigFileMD5(String bigFileName) throws IOException{
		File f = new File(bigFileName);
		return bigFileMD5(f);
	}
	
	/**
	 * 获取大文的MD5计算值
	 * @param bigFile
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:40:35
	 * @writer junehappylove
	 */
	public static String bigFileMD5(File bigFile) throws IOException{
		logger.debug("|当前文件名称:"+bigFile.getName());
		logger.debug("|当前文件大小:"+(bigFile.length()/1024/1024)+"MB");
		logger.debug("|当前文件路径[绝对]:"+bigFile.getAbsolutePath());
		logger.debug("|当前文件路径[---]:"+bigFile.getCanonicalPath());
		InputStream ins = new FileInputStream(bigFile);
		return bigFileMD5(ins);
	}
	
	/**
	 * 获取大文的MD5计算值
	 * @param bigFile
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:30:54
	 * @writer junehappylove
	 */
	public static String bigFileMD5(InputStream bigFileStream) throws IOException{
		byte[] buffer = new byte[8192];
		int len;
		while((len=bigFileStream.read(buffer))!=-1){
			messagedigest.update(buffer, 0, len);
		}
		bigFileStream.close();
		return DigestUtils.md5Hex(messagedigest.digest());
		// 也可用自己写的
		// return bufferToHex(messagedigest.digest());
	}
	
	/**
	 * 获取文件的md5
	 * 请参考apache的方法org.apache.commons.codec.digest.DigestUtils<br>
	 * 如果是过G的大文件推荐使用 {@link #bigFileMD5(InputStream)}方法去获取md5值
	 * @param fis
	 * @return
	 * @throws IOException
	 * @date 2016年12月20日 上午8:27:51
	 * @writer junehappylove
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
