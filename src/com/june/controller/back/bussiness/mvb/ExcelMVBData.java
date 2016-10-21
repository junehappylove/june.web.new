/**
 * JUNE软件有限公司<br>
 * com.june.controller.back.common.ExcelErrorCodeData.java
 * 日期:2016年6月13日
 */
package com.june.controller.back.bussiness.mvb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.june.dto.back.bussiness.mvb.MVBDto;

/**
 * 将excel中故障代码的数据导入数据库工具 <br>
 * 
 * @author 王俊伟 wjw.happy.love@163.com
 * @date 2016年6月13日 上午11:37:53
 */
public class ExcelMVBData {

	private static final ExcelMVBData getInstance = new ExcelMVBData();

	private ExcelMVBData() {
	}

	public static ExcelMVBData getInstance() {
		return getInstance;
	}

	public List<MVBDto> readXls(HSSFWorkbook hssfWorkbook) throws IOException {
		List<MVBDto> list = new ArrayList<MVBDto>();
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
	public List<MVBDto> readXls(HSSFWorkbook hssfWorkbook, int sheet) throws IOException {
		MVBDto mvbDto = null;
		List<MVBDto> list = new ArrayList<MVBDto>();
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheet);
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			HSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow != null) {
				mvbDto = new MVBDto();
				HSSFCell system = hssfRow.getCell(0);// 子系统
				HSSFCell portAddr = hssfRow.getCell(1);// 端口地址
				HSSFCell portSize = hssfRow.getCell(2);// 端口大小
				HSSFCell cycleTime = hssfRow.getCell(3);// 循环时间
				HSSFCell byteOffset = hssfRow.getCell(4);// 字节偏移
				HSSFCell bitOffset = hssfRow.getCell(5);// 位偏移
				HSSFCell signal = hssfRow.getCell(6);// 信号
				HSSFCell desc = hssfRow.getCell(7);// 描述
				HSSFCell max = hssfRow.getCell(8);// 最大值
				HSSFCell min = hssfRow.getCell(9);// 最小值
				HSSFCell type = hssfRow.getCell(10);// 类型
				HSSFCell measure = hssfRow.getCell(11);// 范围
				HSSFCell formula = hssfRow.getCell(12);//规则
				HSSFCell unit = hssfRow.getCell(13);//单位
				HSSFCell flag = hssfRow.getCell(14);//标识
				HSSFCell upper = hssfRow.getCell(15);//上限
				HSSFCell lower = hssfRow.getCell(16);//下限
				HSSFCell defaultValue = hssfRow.getCell(17);//默认值
				
				mvbDto.setSystem(getValue(system));
				mvbDto.setPortAddr(getValue(portAddr));
				mvbDto.setPortSize(Integer.parseInt(getValue(portSize)));
				mvbDto.setCycleTime(Integer.parseInt(getValue(cycleTime)));
				mvbDto.setByteOffset(Integer.parseInt(getValue(byteOffset)));
				mvbDto.setBitOffset(Integer.parseInt(getValue(bitOffset)));
				mvbDto.setSignal(getValue(signal));
				mvbDto.setDescription(getValue(desc));
				mvbDto.setMax((long)Double.parseDouble(getValue(max)));
				mvbDto.setMin((long)Double.parseDouble(getValue(min)));
				mvbDto.setType(getValue(type));
				mvbDto.setMeasure(getValue(measure));
				mvbDto.setFormula(getValue(formula));
				mvbDto.setUnit(getValue(unit));
				mvbDto.setFlag(getValue(flag));
				mvbDto.setUpper((long)Double.parseDouble(getValue(upper)));
				mvbDto.setLower(Integer.parseInt(getValue(lower)));
				mvbDto.setDefaultValue(Integer.parseInt(getValue(defaultValue)));
				
				list.add(mvbDto);
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
		String retStr = null;
		if (hssfCell == null) {
			return "";
		}
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			retStr = String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			retStr = String.valueOf(hssfCell.getNumericCellValue());
		} else if(hssfCell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
			retStr = ""; 
		}else {
			// 返回字符串类型的值
			retStr = String.valueOf(hssfCell.getStringCellValue());
		}
		return retStr.trim();
	}
}
