package com.june.utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CSVUtils {

	/**
	 * 导出csv<br>
	 * 将查询出来的数据导出到csv中并存放到指定文件夹中
	 * 
	 * @param datasource 数据
	 * @param titles
	 * @param attrs
	 * @param sheetname
	 * @param folderpath 目标路径
	 * @param filename  csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @date 2017年1月19日 下午7:40:24
	 * @writer junehappylove
	 */
	public static void exportCsvToFolder(List<?> datasource, String[] titles, String[] attrs, String sheetname,
			String folderpath, String filename)
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(folderpath + filename + ".csv");
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();
			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			// 写入文件头部
			for (int i = 0; i < titles.length; i++) {
				csvFileOutputStream.write("\"" + titles[i] + "\"");
				if (i != titles.length - 1) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();
			if (datasource != null && datasource.size() > 0) {
				for (int j = 0; j < datasource.size(); j++) {
					for (int i = 0; i < attrs.length; i++) {
						Class<? extends Object> clazz = datasource.get(j).getClass();

						Method[] methods = clazz.getMethods();// 获取方法
						for (Method method : methods) {
							String methodName = method.getName().toUpperCase();
							String methodName1 = ("get" + attrs[i]).toUpperCase();
							if (methodName.equals(methodName1)) {
								Object value = method.invoke(datasource.get(j));
								csvFileOutputStream.write("\"" + value.toString() + "\"");
							}
						}
						if (i != attrs.length - 1) {
							csvFileOutputStream.write(",");
						}
					}
					if (j != datasource.size() - 1) {
						csvFileOutputStream.newLine();
					}
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
