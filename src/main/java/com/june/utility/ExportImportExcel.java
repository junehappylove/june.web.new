package com.june.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.june.common.AbstractDTO;

/**
 * 
 * Excel表操作工具 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月27日 下午1:50:30
 */
public class ExportImportExcel {

	/**
	 * 将查询出来的数据导出到excel中并在页面进行下载
	 * @param datasource
	 * @param titles
	 * @param attrs
	 * @param response
	 * @param sheetname
	 * @param filename
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @date 2016年6月27日 下午1:50:26
	 * @writer wjw.happy.love@163.com
	 */
	public static void exportExcelForDownload(List<?> datasource, String[] titles, String[] attrs,
			HttpServletResponse response, String sheetname, String filename)
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		response.setContentType("octets/stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename + ".xls");
		ServletOutputStream outputStream = response.getOutputStream();
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(sheetname);
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (datasource != null && datasource.size() > 0) {
			for (int j = 0; j < datasource.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				for (int i = 0; i < attrs.length; i++) {
					cell = bodyRow.createCell(i);
					cell.setCellStyle(bodyStyle);
					@SuppressWarnings("rawtypes")
					Class clazz = datasource.get(j).getClass();

					Method[] methods = clazz.getMethods();// 获取方法
					for (Method method : methods) {
						String methodName = method.getName().toUpperCase();
						String methodName1 = ("get" + attrs[i]).toUpperCase();
						if (methodName.equals(methodName1)) {
							Object value = method.invoke(datasource.get(j));
							// Class methodType = method.getReturnType();
							if (value == null) {
								cell.setCellValue("");
							} else {
								cell.setCellValue(value.toString());
							}
						}
					}
				}
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将查询出来的数据导出到excel中并存放到指定文件夹中
	 * @param datasource
	 * @param titles
	 * @param attrs
	 * @param sheetname
	 * @param folderpath
	 * @param filename
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @date 2016年6月27日 下午1:49:22
	 * @writer wjw.happy.love@163.com
	 */
	public static void exportExcelToFolder(List<?> datasource, String[] titles, String[] attrs, String sheetname,
			String folderpath, String filename)
					throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		// 文件夹是否存在，不存在创建
		File file = new File(folderpath);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(sheetname);
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (datasource != null && datasource.size() > 0) {
			for (int j = 0; j < datasource.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				for (int i = 0; i < attrs.length; i++) {
					cell = bodyRow.createCell(i);
					cell.setCellStyle(bodyStyle);
					@SuppressWarnings("rawtypes")
					Class clazz = datasource.get(j).getClass();

					Method[] methods = clazz.getMethods();// 获取方法
					for (Method method : methods) {
						String methodName = method.getName().toUpperCase();
						String methodName1 = ("get" + attrs[i]).toUpperCase();
						if (methodName.equals(methodName1)) {
							Object value = method.invoke(datasource.get(j));
							// Class methodType = method.getReturnType();
							cell.setCellValue(value.toString());
						}
					}
				}
			}
		}
		// 输出工作簿
		FileOutputStream fos = new FileOutputStream(folderpath + filename + ".xls");
		workBook.write(fos);
		// 关闭输出流
		fos.close();
	}

	/**
	 *  读取excel中的数据
	 * @param startRomNum
	 * @param is
	 * @param abstractDTO
	 * @param colomns
	 * @param attrs
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @date 2016年6月27日 下午1:49:32
	 * @writer wjw.happy.love@163.com
	 */
	public static List<?> readXls(int startRomNum, InputStream is, AbstractDTO abstractDTO, int[] colomns,
			String[] attrs) throws IOException, IllegalAccessException, IllegalArgumentException,
					InvocationTargetException, InstantiationException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<AbstractDTO> DataFromExcel = new ArrayList();
		// 获取文件
		// InputStream is = new FileInputStream(filepath);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = startRomNum; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					@SuppressWarnings("rawtypes")
					Class clazz = abstractDTO.getClass();
					Method[] methods = clazz.getMethods();// 获取方法
					for (int i = 0; i < colomns.length; i++) {
						XSSFCell xssfCell = xssfRow.getCell(colomns[i]);
						for (Method method : methods) {
							String methodName = method.getName().toUpperCase();
							String methodName1 = ("set" + attrs[i]).toUpperCase();
							if (methodName.equals(methodName1)) {
								if (xssfCell == null) {
									method.invoke(abstractDTO, "");
								} else {
									method.invoke(abstractDTO, getValue(xssfCell));
								}
							}
						}
					}
				}
				DataFromExcel.add(abstractDTO);
				abstractDTO = abstractDTO.getClass().newInstance();
			}
		}
		return DataFromExcel;
	}

	/**
	 * 直接读取前台传过来的excel文件中的数据
	 * @param startRomNum
	 * @param myfiles
	 * @param abstractDTO
	 * @param colomns
	 * @param attrs
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @date 2016年6月27日 下午1:49:40
	 * @writer wjw.happy.love@163.com
	 */
	public static List<?> readpassXls(int startRomNum, MultipartFile[] myfiles, AbstractDTO abstractDTO, int[] colomns,
			String[] attrs) throws IOException, IllegalAccessException, IllegalArgumentException,
					InvocationTargetException, InstantiationException {
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<AbstractDTO> DataFromExcel = new ArrayList();
		// 获取文件
		InputStream is = myfiles[0].getInputStream();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = startRomNum; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null) {
					@SuppressWarnings("rawtypes")
					Class clazz = abstractDTO.getClass();
					Method[] methods = clazz.getMethods();// 获取方法
					for (int i = 0; i < colomns.length; i++) {
						XSSFCell xssfCell = xssfRow.getCell(colomns[i]);
						for (Method method : methods) {
							String methodName = method.getName().toUpperCase();
							String methodName1 = ("set" + attrs[i]).toUpperCase();
							if (methodName.equals(methodName1)) {
								if (xssfCell == null) {
									method.invoke(abstractDTO, "");
								} else {
									method.invoke(abstractDTO, getValue(xssfCell));
								}
							}
						}
					}
				}
				DataFromExcel.add(abstractDTO);
				abstractDTO = abstractDTO.getClass().newInstance();
			}
		}

		return DataFromExcel;
	}

	/**
	 * 根据单元格的数据类型返回值
	 * @param cell
	 * @return
	 * @date 2016年6月27日 下午1:49:50
	 * @writer wjw.happy.love@163.com
	 */
	private static String getValue(XSSFCell cell) {
		// 返回值
		String str = null;
		if (cell != null) {
			// 单元格类型
			int cellType = cell.getCellType();
			switch (cellType) {
			case Cell.CELL_TYPE_BLANK:
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				boolean bl = cell.getBooleanCellValue();
				if (bl) {
					str = "true";
				} else {
					str = "false";
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				str = String.valueOf(cell.getNumericCellValue());
				if (str.endsWith(".0")) {
					str = str.replace(".0", "");
				}
				break;
			case Cell.CELL_TYPE_STRING:
				str = cell.getStringCellValue();
				if ("".equals(str)) {
					str = null;
				}
				break;
			case Cell.CELL_TYPE_FORMULA:
				str = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				break;
			default:
				break;
			}
		}
		return str;
	}
}
