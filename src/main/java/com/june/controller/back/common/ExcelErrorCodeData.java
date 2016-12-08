/**
 * JUNE软件有限公司<br>
 * com.june.controller.back.common.ExcelErrorCodeData.java
 * 日期:2016年6月13日
 */
package com.june.controller.back.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.june.dto.back.bussiness.errorCode.ErrorCodeDto;

/**
 * 将excel中故障代码的数据导入数据库工具 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月13日 上午11:37:53
 */
public class ExcelErrorCodeData {

	private static final ExcelErrorCodeData getInstance = new ExcelErrorCodeData();

	private ExcelErrorCodeData() {
	}

	public static ExcelErrorCodeData getInstance() {
		return getInstance;
	}

	public List<ErrorCodeDto> readXls(HSSFWorkbook hssfWorkbook) throws IOException {
		List<ErrorCodeDto> list = new ArrayList<ErrorCodeDto>();
		int num = hssfWorkbook.getNumberOfSheets();
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < num; numSheet++) {
			list.addAll(readXls(hssfWorkbook, numSheet));
		}
		return list;
	}
	
	/**
	 * InputStream is = new FileInputStream(Common.EXCEL_PATH); HSSFWorkbook
	 * hssfWorkbook = new HSSFWorkbook(is);
	 * 
	 * @param hssfWorkbook
	 * @return
	 * @throws IOException
	 * @date 2016年6月13日 下午12:39:10
	 * @writer wjw.happy.love@163.com
	 */
	public List<ErrorCodeDto> readXls(HSSFWorkbook hssfWorkbook, int sheet) throws IOException {
		ErrorCodeDto errorCode = null;
		List<ErrorCodeDto> list = new ArrayList<ErrorCodeDto>();
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheet);
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null) {
				errorCode = new ErrorCodeDto();
				HSSFCell codeid = hssfRow.getCell(0);// 故障代码
				HSSFCell subSystem = hssfRow.getCell(1);// 子系统
				HSSFCell errorLevel = hssfRow.getCell(2);// 故障等级
				HSSFCell vehicle = hssfRow.getCell(3);// 影响车辆
				HSSFCell errorReason = hssfRow.getCell(4);// 故障描述（简短描述）
				HSSFCell errorDesc = hssfRow.getCell(5);// 故障描述（详细描述）
				HSSFCell guidV0 = hssfRow.getCell(6);// 司机操作提示(V=0)
				HSSFCell guidV1 = hssfRow.getCell(7);// 司机操作提示(V>0)
				HSSFCell guidV2 = hssfRow.getCell(8);// 检修操作提示
				HSSFCell port = hssfRow.getCell(9);// 端口号
				HSSFCell wordOffset = hssfRow.getCell(10);// 字节偏移
				HSSFCell byteOffset = hssfRow.getCell(11);// 位偏移
				errorCode.setErrorCode(getValue(codeid));
				//子系统空，则默认为其他
				errorCode.setSubSystem(getValue(subSystem).equals("")?"其他":getValue(subSystem));
				errorCode.setErrorLevel(getValue(errorLevel));
				errorCode.setVehicle(getValue(vehicle));
				errorCode.setErrorReason(getValue(errorReason));
				errorCode.setErrorDesc(getValue(errorDesc));
				errorCode.setGuidV0(getValue(guidV0));
				errorCode.setGuidV1(getValue(guidV1));
				errorCode.setGuidV2(getValue(guidV2));
				errorCode.setPort(getValue(port));
				errorCode.setWordOffset(getValue(wordOffset));
				errorCode.setByteOffset(getValue(byteOffset));
				if (StringUtils.isNotEmpty((errorCode.getErrorCode()))) {
					// 故障代码为空此记录舍弃
					list.add(errorCode);
				}
			}
		}
		return list;
	}

	/**
	 * 取值到字符串
	 * @param hssfCell
	 * @return
	 * @date 2016年6月29日 上午9:21:28
	 * @writer wjw.happy.love@163.com
	 */
	private String getValue(HSSFCell hssfCell) {
		String retStr = "";
		if (hssfCell == null) {
			return "";
		}
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			retStr = String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			retStr = String.valueOf((int)hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			retStr = String.valueOf(hssfCell.getStringCellValue());
		}
		return retStr.trim();
	}
}
