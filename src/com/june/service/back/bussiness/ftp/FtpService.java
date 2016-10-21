package com.june.service.back.bussiness.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.june.common.BaseService;
import com.june.common.Constants;
import com.june.dao.back.bussiness.ftp.FtpDao;
import com.june.dto.back.bussiness.ftp.FtpConfig;
import com.june.dto.back.bussiness.ftp.FtpDto;
import com.june.dto.back.bussiness.ftp.FtpFile;
import com.june.utility.ApacheFTP;
import com.june.utility.FtpTree;

/**
 * 
 * FTP设置信息Service <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年5月12日 下午5:42:59
 */
@Service
public class FtpService extends BaseService<FtpDao,FtpDto> {

	/**
	 * FTP设置信息dao
	 */
	@Autowired
	protected FtpDao ftpDao;

	/**
	 * 取当前有效的FTP配置信息
	 * 
	 * @return
	 * @date 2016年6月14日 下午1:38:29
	 * @writer wjw.happy.love@163.com
	 */
	public FtpDto getFtpConfig(){
		return FtpConfig.get();//获取默认配置
	}
	
	public ApacheFTP init() throws SocketException, IOException{
		ApacheFTP ftpUtil = ApacheFTP.getInstance();
		FtpConfig ff = FtpConfig.get();
		ftpUtil.connectServer(ff.getFtpIP(), ff.getPort(), ff.getFtpUser(), ff.getFtpPassword(), ff.getFtpPath());
		return ftpUtil;
	}
	
	/**
	 * 创建FTP远程一个目录,如果存在子目录集，也创建
	 * @param ftpDto
	 * @return 返回值的意义不大，true和false并不代表创建是否成功
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年6月22日 上午9:41:18
	 * @writer wjw.happy.love@163.com
	 */
	public boolean createDirectory(FtpDto ftpDto) throws SocketException, IOException{
		ApacheFTP ftpUtil = init();
		String fp = ftpDto.getFtpPath();
		boolean bool = ftpUtil.createDirectory(fp);//创建根目录
		String[] paths = ftpDto.getPaths();//获取子目录集
		if(paths != null){ // 创建目录集
			ftpUtil.changeDirectory(fp);//切换到这个目录下
			for(String path : paths){
				bool = ftpUtil.createDirectory(path);
			}
		}
		ftpUtil.closeServer();
		return bool;
	}
	
	/**
	 * 删除目录以及子目录以及文件
	 * @param ftpDto
	 * @throws Exception
	 * @date 2016年6月14日 下午2:24:29
	 * @writer wjw.happy.love@163.com
	 */
	public boolean deleteDirectory(FtpDto ftpDto) throws Exception{
		ApacheFTP ftpUtil = init();
		boolean bool = ftpUtil.removeDirectory(ftpDto.getFtpPath(), true);
		ftpUtil.closeServer();
		return bool;
	}
	
	public boolean deleteDirectory(String ftpPath) throws SocketException, IOException{
		ApacheFTP ftpUtil = init();
		boolean bool = ftpUtil.removeDirectory(ftpPath, true);
		ftpUtil.closeServer();
		return bool;
	}
	
	/**
	 * 删除目录下的所有文件
	 * @param path
	 * @return 返回值意义不大
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年7月5日 下午4:55:48
	 * @writer wjw.happy.love@163.com
	 */
	public boolean deleteFiles(String path) throws SocketException, IOException{
		ApacheFTP ftpUtil = init();
		List<String> list = ftpUtil.getFileList(path);
		ftpUtil.changeDirectory(path);
		boolean result = false;
		for(String name : list){
			result = ftpUtil.deleteFile(name);
		}
		return result;
	}
	
	/**
	 * 获取ftp目录下文件列表
	 * @param ftpDto
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年6月14日 下午2:20:43
	 * @writer wjw.happy.love@163.com
	 */
	public List<String> getFileList(FtpDto ftpDto) throws SocketException, IOException {
		ApacheFTP ftpUtil = init();
		List<String> list = ftpUtil.getFileList(ftpDto.getFtpPath());
		System.err.println(list);
		ftpUtil.closeServer();
		return list;
	}
	
	/**
	 * 取目录下一个文件名称，取第一个文件
	 * @param path
	 * @return 成功返回文件名，不成功返回空串
	 * @throws IOException 
	 * @date 2016年7月6日 下午2:44:12
	 * @writer wjw.happy.love@163.com
	 */
	public String getFileName(String path) throws IOException{
		List<String> list = getFileNames(path);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return "";
		}
	}
	
	/**
	 * 根据类型取一个文件名，取第一个匹配的
	 * @param path
	 * @param type
	 * @return 成功返回文件名，不成功返回空串
	 * @throws IOException
	 * @date 2016年7月6日 下午3:03:34
	 * @writer wjw.happy.love@163.com
	 */
	public String getFileNameByType(String path,String type) throws IOException{
		List<String> list = getFileNames(path);
		String str = "";
		int size = type.length(); 	// 类型的长度
		for (String name : list) {
			int count = name.length();	// 总长度
			int len = name.lastIndexOf(type);
			if (len + size == count) {
				str = name;
				break;
			} else {
				continue;
			}
		}
		return str;
	}
	
	public static void main(String[] args) {
		String str1 = "01.txt.789012.txt";
		String str2 = "0123456.txt.23456";
		String str3 = "0123456.tx1t.3456";
		String str4 = ".txt.567890123456";
		String str5 = "王俊伟.txt.567890";
		System.out.println(str1.lastIndexOf(".txt"));
		System.out.println(str2.lastIndexOf(".txt"));
		System.out.println(str3.lastIndexOf(".txt"));
		System.out.println(str4.lastIndexOf(".txt"));
		System.out.println(str5.lastIndexOf(".txt"));
		System.out.println(str1.length());
	}
	
	public List<String> getFileNames(String path) throws IOException{
		ApacheFTP ftpUtil = init();
		List<String> list = ftpUtil.getFileList(path);
		ftpUtil.closeServer();
		return list;
	}
	
	/**
	 * 获取ftp目录结构
	 * @param rootPath
	 * @return
	 * @throws IOException
	 * @date 2016年6月27日 下午6:42:18
	 * @writer wjw.happy.love@163.com
	 */
	public List<FtpTree> getFtpTree(String rootPath) throws IOException{
		ApacheFTP ftpUtil = init();
		List<FtpTree> list = ftpUtil.getFtpTree(rootPath);
		ftpUtil.closeServer();
		return list;
	}
	
	/**
	 * 获取ftp目录下的文件信息
	 * @param ftpPath
	 * @return
	 * @throws IOException 
	 * @date 2016年6月25日 上午10:02:48
	 * @writer wjw.happy.love@163.com
	 */
	public List<FtpFile> getFtpFiles(String ftpPath) throws IOException{
		List<FtpFile> list = new ArrayList<FtpFile>();
		FtpFile file = new FtpFile();;
		String url=null;
		ApacheFTP ftpUtil = init();
		List<String> slist = ftpUtil.getFileList(ftpPath);
		for(String name:slist){
			url = ftpPath + "/" + name;
			file.setFileName(name);
			file.setFileUrl(url);
			list.add(file);
		}
		ftpUtil.closeServer();
		return list;
	}
	
	/**
	 * 上传文件
	 * @param ftpDto
	 * @return 返回值参考意义不大
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年6月22日 上午11:11:18
	 * @writer wjw.happy.love@163.com
	 */
	public boolean uploadFile(FtpDto ftpDto) throws SocketException, IOException{
		ApacheFTP ftpUtil = init();
		boolean result = false;
		ftpUtil.createDirectory(ftpDto.getFtpPath());			//先创建这个目录
		result = ftpUtil.changeDirectory(ftpDto.getFtpPath());	//切换到此目录下
		Map<String, InputStream> map = ftpDto.getFileMap();
		if(map!=null){
			InputStream is = null;
			String fileName = "";
			Set<String> set = map.keySet();
			for(Iterator<String> it = set.iterator();it.hasNext();){
				fileName = it.next();
				is = map.get(fileName);
				result = ftpUtil.uploadFile(is, fileName);
			}
			ftpUtil.closeServer();
		}
		return result;
	}
	
	/**
	 * 下载文件
	 * @param ftpDto
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 * @date 2016年6月27日 下午1:04:56
	 * @writer wjw.happy.love@163.com
	 */
	public InputStream fileStream(FtpDto ftpDto) throws SocketException, IOException{
		ApacheFTP ftpUtil = init();
		ftpUtil.changeDirectory(ftpDto.getFtpPath());//切换到目录下
		InputStream is = ftpUtil.downFile(ftpDto.getFtpFileName());//下载这个目录下的这个文件
		ftpUtil.closeServer();
		return is ;
	}
	
	/**
	 * 下载文件
	 * @param path ftp目录
	 * @param name 文件名称
	 * @return
	 * @throws IOException
	 * @date 2016年6月27日 下午1:09:58
	 * @writer wjw.happy.love@163.com
	 */
	public boolean downloadFile(String path,String name) throws IOException {
		ApacheFTP ftpUtil = init();
		boolean result = false;
		result = ftpUtil.changeDirectory(path);//切换到目录下
		result = ftpUtil.download(name, Constants.DIRECTORY_LOCAL_DOWNLOAD + name);
		ftpUtil.closeServer();
		return result ;
	}
	
	public boolean downloadFile(String pathFile) throws IOException {
		ApacheFTP ftpUtil = init();
		boolean result = false;
		String name = pathFile.substring(pathFile.lastIndexOf("/"));
		String path = pathFile.replace(name, ""); 
		result = ftpUtil.changeDirectory(path);//切换到目录下
		result = ftpUtil.download(pathFile, Constants.DIRECTORY_LOCAL_DOWNLOAD + name);
		ftpUtil.closeServer();
		return result ;
	}
	
	/**
	 * 下载文件
	 * @param path
	 * @param name
	 * @param newName
	 * @return
	 * @throws IOException
	 * @date 2016年6月29日 下午6:55:48
	 * @writer wjw.happy.love@163.com
	 */
	public boolean downloadFile(String path,String name,String newName) throws IOException {
		ApacheFTP ftpUtil = init();
		boolean result = false;
		result = ftpUtil.changeDirectory(path);//切换到目录下
		result = ftpUtil.download(name, Constants.DIRECTORY_LOCAL_DOWNLOAD + newName);
		ftpUtil.closeServer();
		return result ;
	}

	/**
	 * 复制一个FTP目录到另一个目录下
	 * 
	 * @param sourcePath
	 * @param toPath
	 * @throws IOException 
	 * @throws SocketException 
	 * @date 2016年6月22日 下午2:49:43
	 * @writer wjw.happy.love@163.com
	 */
	public boolean copyDirectory(String sourcePath, String toPath) throws SocketException, IOException {
		ApacheFTP ftpUtil = init();
		boolean result = false;
		ftpUtil.copyDirectiory(sourcePath, toPath);
		ftpUtil.closeServer();
		return result;
	}
	
	/**
	 * 复制一个ftp文件到另一个目录下
	 * @param fileName 文件名称
	 * @param sourceDir 包含目录
	 * @param targetDir 目标目录下
	 * @return 参考意义不大
	 * @throws IOException 
	 * @date 2016年7月6日 下午9:35:11
	 * @writer wjw.happy.love@163.com
	 */
	public boolean copyFile(String fileName,String sourceDir,String targetDir) throws IOException{
		ApacheFTP ftpUtil = init();
		boolean result = false;
		ftpUtil.copyFile(fileName, sourceDir, targetDir);
		ftpUtil.closeServer();
		return result;
	}
}
