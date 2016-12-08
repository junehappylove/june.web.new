/**
 * JUNE软件有限公司<br>
 * com.june.utility.ApacheFTP.java
 * 日期:2016年6月14日
 */
package com.june.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * ApacheFTP <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月14日 上午11:12:55
 */
public class ApacheFTP {
	
	private static final ApacheFTP ins = new ApacheFTP();
	private FTPClient ftpClient;
	public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
	public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;
	
	public final List<FtpTree> tree = new ArrayList<FtpTree>();

	private ApacheFTP() {
	}

	public static ApacheFTP getInstance() {
		return ins;
	}

	/**
	 * 使用详细信息进行服务器连接
	 * 
	 * @param server：服务器地址名称
	 * @param port：端口号
	 * @param user：用户名
	 * @param password：用户密码
	 * @param path：转移到FTP服务器目录
	 * @throws SocketException
	 * @throws IOException
	 */
	public void connectServer(String server, int port, String user, String password, String path)
			throws SocketException, IOException {
		ftpClient = new FTPClient();
		ftpClient.connect(server, port);
		System.err.print("Connected to " + server + "\tReturn ");
		// 连接成功后的回应码
		System.err.println(ftpClient.getReplyCode());
		ftpClient.login(user, password);
		if (path != null && path.length() != 0) {
			ftpClient.changeWorkingDirectory($zh(path));
		}
		ftpClient.setBufferSize(10240);// 设置上传缓存大小
		ftpClient.setControlEncoding(Const.DEFAULT_ENCODE);// 设置编码
		ftpClient.setFileType(BINARY_FILE_TYPE);// 设置文件类型
	}

	/**
	 * 设置传输文件类型:FTP.BINARY_FILE_TYPE | FTP.ASCII_FILE_TYPE 二进制文件或文本文件
	 * 
	 * @param fileType
	 * @throws IOException
	 */
	public void setFileType(int fileType) throws IOException {
		ftpClient.setFileType(fileType);
	}

	/**
	 * 关闭连接
	 * 
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		if (ftpClient != null && ftpClient.isConnected()) {
			ftpClient.logout();// 退出FTP服务器
			ftpClient.disconnect();// 关闭FTP连接
		}
	}

	/**
	 * 转移到FTP服务器工作目录
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory($zh(path));
	}

	/**
	 * 在服务器上创建目录
	 * 如果目录不存在，创建成功返回true;
	 * 如果目录存在，则返回fasle;
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean createDirectory(String pathName) throws IOException {
		pathName = pathName.replace("/", "/");
		pathName = pathName.replace("\\", "/");
		/*
		if(StringUtil.isNotEmpty(pathName) && pathName.charAt(0)=='/'){
			pathName = pathName.replaceFirst("/", "");
		}//*/
		String[] dirs = pathName.split("/");
		StringBuffer path = new StringBuffer();
		boolean ret = false;
		for (String dir : dirs) {
			path.append(dir + "/");
			ret = ftpClient.makeDirectory($zh(path.toString()));
		}
		return ret;
	}

	/**
	 * 在服务器上删除目录
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path) throws IOException {
		path = $zh(path);
		return ftpClient.removeDirectory(path);
	}

	/**
	 * 删除所有文件和目录
	 * 
	 * @param path
	 * @param isAll
	 *            true:删除所有文件和目录
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(String path, boolean isAll) throws IOException {
		if (!isAll) {
			return removeDirectory(path);
		}

		FTPFile[] ftpFileArr = ftpClient.listFiles($zh(path));
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		// 如果有子文件
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				removeDirectory(path + File.separator + name, true);
			} else if (ftpFile.isFile()) {
				deleteFile(path + File.separator + name);
			} else if (ftpFile.isSymbolicLink()) {

			} else if (ftpFile.isUnknown()) {

			}
		}
		return ftpClient.removeDirectory($zh(path));
	}

	/**
	 * 检查目录在服务器上是否存在 true：存在 false：不存在
	 * 
	 * @deprecated 以经过时
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean existDirectory(String path) throws IOException {
		String codePath = $zh(path);
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(codePath);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 得到文件列表,listFiles返回包含目录和文件，它返回的是一个FTPFile数组 listNames()：只包含目录的字符串数组
	 * String[] fileNameArr = ftpClient.listNames(path);
	 * 
	 * @param path:服务器上的文件目录:/FTP
	 */
	public List<String> getFileList(String path) throws IOException {
		path = $zh(path);
		FTPFile[] ftpFiles = ftpClient.listFiles(path);
		// 通过FTPFileFilter遍历只获得文件
		/*
		 * FTPFile[] ftpFiles2= ftpClient.listFiles(path,new FTPFileFilter() {
		 * 
		 * @Override public boolean accept(FTPFile ftpFile) { return
		 * ftpFile.isFile(); } });
		 */
		List<String> retList = new ArrayList<String>();
		if (ftpFiles == null || ftpFiles.length == 0) {
			return retList;
		}
		for (FTPFile ftpFile : ftpFiles) {
			if (ftpFile.isFile()) {
				retList.add(ftpFile.getName());
			}
		}
		return retList;
	}

	/**
	 * 获取一个目录下的文件和目录
	 * @param path
	 * @return
	 * @throws IOException
	 * @date 2016年6月27日 下午6:39:49
	 * @writer wjw.happy.love@163.com
	 */
	public List<FtpTree> trees(String path) throws IOException{
		FtpTree ftp = null;
		String path2 = $zh(path);
		String name = null;
		List<FtpTree> tree = new ArrayList<FtpTree>();
		FTPFile[] ftpFiles = ftpClient.listFiles(path2);
		for (FTPFile file : ftpFiles) {
			ftp = new FtpTree();
			name = file.getName();
			ftp.setName(name);
			ftp.setPath(path);
			// TODO 下载的时候用这个文件名称
			ftp.setPath_(MD5Util.getMD5(path+name)+getFileType(name));
			ftp.setId(name);
			ftp.setPid(path);
			if (file.isFile()) {
				ftp.setFile(true);
				//ftp.setUrl(path + "/" + file.getName());
				ftp.setUrl(null);
			} else {
				ftp.setFile(false);
				ftp.setUrl(null);
			}
			tree.add(ftp);
		}
		return tree;
	}
	
	
	public String getFileType(String str){
		String type = null;
		int size = str.lastIndexOf(".");
		if (size > 0) {
			type = str.substring(size);
		}
		return type;
	}
	
	/**
	 * 获取一个目录下的所有目录和文件，递归实现
	 * @param root
	 * @return
	 * @throws IOException
	 * @date 2016年6月27日 下午6:39:13
	 * @writer wjw.happy.love@163.com
	 */
	public List<FtpTree> getFtpTree(String root) throws IOException{
		List<FtpTree> tree = trees(root);
		tree = treeSecond(tree);
		return tree;
	}
	
	/**
	 * 递归实现获取二级目录和文件
	 * @param first
	 * @return
	 * @throws IOException
	 * @date 2016年6月27日 下午6:40:16
	 * @writer wjw.happy.love@163.com
	 */
	private List<FtpTree> treeSecond(List<FtpTree> first) throws IOException {
		List<FtpTree> children = null;
		String path = null;
		if (first != null) {
			for (FtpTree tree : first) {
				if (!tree.isFile()) {
					path = tree.getPath() + "/" + tree.getName();
					children = trees(path);
					tree.setChildren(children);
					treeSecond(tree.getChildren());
				}
			}
		}
		return first;
	}
	
	/**
	 * 删除服务器上的文件
	 * 
	 * @param pathName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(String pathName) throws IOException {
		pathName = $zh(pathName);
		return ftpClient.deleteFile(pathName);
	}

	/**
	 * 上传文件到ftp服务器 在进行上传和下载文件的时候，设置文件的类型最好是：
	 * ftpUtil.setFileType(FtpUtil.BINARY_FILE_TYPE) localFilePath:本地文件路径和名称
	 * remoteFileName:服务器文件名称
	 */
	public boolean uploadFile(String localFilePath, String remoteFileName) throws IOException {
		localFilePath = $zh(localFilePath);
		remoteFileName = $zh(remoteFileName);
		boolean flag = false;
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(localFilePath);
			// 我们可以使用BufferedInputStream进行封装
			// BufferedInputStream bis=new BufferedInputStream(iStream);
			// flag = ftpClient.storeFile(remoteFileName, bis);
			flag = ftpClient.storeFile(remoteFileName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 上传文件到ftp服务器，上传新的文件名称和原名称一样
	 * 
	 * @param fileName：文件名称
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(String fileName) throws IOException {
		fileName = $zh(fileName);
		return uploadFile(fileName, fileName);
	}

	/**
	 * 上传文件到ftp服务器
	 * 
	 * @param iStream
	 *            输入流
	 * @param newName
	 *            新文件名称
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(InputStream iStream, String newName) throws IOException {
		newName = $zh(newName);
		boolean flag = false;
		try {
			flag = ftpClient.storeFile(newName, iStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			if (iStream != null) {
				iStream.close();
			}
		}
		return flag;
	}

	/**
	 * 从ftp服务器上下载文件到本地
	 * 
	 * @param remoteFileName：ftp服务器上文件名称
	 * @param localFileName：本地文件名称
	 * @return
	 * @throws IOException
	 */
	public boolean download(String remoteFileName, String localFileName) throws IOException {
		remoteFileName = $zh(remoteFileName);
		//localFileName = $zh(localFileName); //本地文件名不需要编码
		boolean flag = false;
		File outfile = new File(localFileName);
		OutputStream oStream = null;
		try {
			oStream = new FileOutputStream(outfile);
			// 可以使用BufferedOutputStream进行封装
			// BufferedOutputStream bos=new BufferedOutputStream(oStream);
			// flag = ftpClient.retrieveFile(remoteFileName, bos);
			flag = ftpClient.retrieveFile(remoteFileName, oStream);
		} catch (IOException e) {
			flag = false;
			return flag;
		} finally {
			oStream.close();
		}
		return flag;
	}

	/**
	 * 从ftp服务器上下载文件到本地
	 * 
	 * @param sourceFileName：服务器资源文件名称
	 * @return InputStream 输入流
	 * @throws IOException
	 */
	public InputStream downFile(String sourceFileName) throws IOException {
		sourceFileName = $zh(sourceFileName);
		return ftpClient.retrieveFileStream(sourceFileName);
	}

	/** 
	 * 复制文件夹. 
	 *  
	 * @param sourceDir 
	 * @param targetDir 
	 * @throws IOException 
	 */  
	public void copyDirectiory(String sourceDir, String targetDir) throws IOException {  
	    // 新建目标目录  
        createDirectory(targetDir);
	    if (!existDirectory(targetDir)) {  
	        createDirectory(targetDir);  
	    }  
	    // 获取源文件夹当前下的文件或目录  
	    FTPFile[] ftpFiles = ftpClient.listFiles($zh(sourceDir));  
	    for (int i = 0; i < ftpFiles.length; i++) {  
	        if (ftpFiles[i].isFile()) {  
	            copyFile(ftpFiles[i].getName(), sourceDir, targetDir);  
	        } else if (ftpFiles[i].isDirectory()) {  
	            copyDirectiory(sourceDir + File.separator + ftpFiles[i].getName(), targetDir + File.separator + ftpFiles[i].getName());  
	        }  
	    }  
	}
	
	/** 
	 * 复制文件. 
	 *  
	 * @param sourceFileName 
	 * @param targetFile 
	 * @throws IOException 
	 */  
	public void copyFile(String sourceFileName, String sourceDir, String targetDir) throws IOException {  
	    ByteArrayInputStream in = null;  
	    ByteArrayOutputStream fos = new ByteArrayOutputStream();  
	    try {  
            createDirectory(targetDir);
	        if (!existDirectory(targetDir)) {
	            createDirectory(targetDir);
	        }
	        ftpClient.setBufferSize(1024 * 2);  
	        // 变更工作路径  
	        ftpClient.changeWorkingDirectory($zh(sourceDir));  
	        // 设置以二进制流的方式传输  
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
	        // 将文件读到内存中  
	        ftpClient.retrieveFile($zh(sourceFileName), fos);  
	        in = new ByteArrayInputStream(fos.toByteArray());  
	        if (in != null) {  
	            ftpClient.changeWorkingDirectory($zh(targetDir));  
	            ftpClient.storeFile($zh(sourceFileName), in);  
	        }  
	    } finally {  
	        // 关闭流  
	        if (in != null) {  
	            in.close();  
	        }  
	        if (fos != null) {  
	            fos.close();  
	        }  
	    }  
	}
	
	/**
	 * ftp文件操作，更改编码
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 * @date 2016年6月22日 下午2:55:02
	 * @writer wjw.happy.love@163.com
	 */
	String $zh(String str) throws UnsupportedEncodingException {
		str = new String(str.getBytes("GBK"), "iso-8859-1");
		return str;
	}
	
	public static void main(String[] args) throws SocketException, IOException {
		ApacheFTP ftpUtil = new ApacheFTP();
		ftpUtil.connectServer("192.168.100.5", FTPClient.DEFAULT_PORT, "nfschina", "nfschina", null);
		// 获得ftp服务器上目录名称为DF4下的所有文件名称
		List<String> list = ftpUtil.getFileList("");
		System.out.println("文件名称列表为:" + list);
		// 上传本地D盘文件aaa.txt到服务器，服务器上名称为bbb.txt
		//ftpUtil.uploadFile("d:" + File.separator + "aaa.txt", "eee.txt");
		// 从服务器上下载文件bbb.txt到本地d盘名称为ccc.txt
		//ftpUtil.download("eee.txt", "d:" + File.separator + "fff.txt");
		// 删除ftp服务器上文件:bbb.txt
		// ftpUtil.deleteFile("bbb.txt");
		//ftpUtil.createDirectory("/12啊啊啊");
		//ftpUtil.removeDirectory("/12啊", true);
		//ftpUtil.getFtpTree("/车型A1");
		System.out.println(ftpUtil.getFileType("adfsad.dsaadf.exe"));
	}
}
