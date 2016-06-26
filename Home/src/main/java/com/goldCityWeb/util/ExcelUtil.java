package com.goldCityWeb.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import jxl.biff.DataValidation;

import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class ExcelUtil {

	class InformationSystem {
		private int id;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	List<InformationSystem> systemList = new ArrayList<InformationSystem>();
	List<String> systemNames = new ArrayList<String>();

	/**
	 * 
	 * @param request   带文件的请求流
	 * @param ignoreRows   开始行数
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static List<List<String>> getData(HttpServletRequest request, int ignoreRows)
			throws IOException, InvalidFormatException {
		if (request instanceof MultipartHttpServletRequest) {
			List<String[]> result = new ArrayList<String[]>();

			int rowSize = 0;

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multiRequest.getFile("excel");

			InputStream in = mf.getInputStream();

			Workbook wb = null;
			// 打开HSSFWorkbook
			/*
			 * POIFSFileSystem fs = new POIFSFileSystem(in);
			 */

			if (in.available() > 0) {
				if (!in.markSupported()) {
					in = new PushbackInputStream(in, 8);
				}
				if (POIFSFileSystem.hasPOIFSHeader(in)) {// 2003
					wb = new HSSFWorkbook(in);
					// List<HSSFPictureData>
					// pictures=wb.getAllPictures();
				} else if (POIXMLDocument.hasOOXMLHeader(in)) {// 2007
					wb = new XSSFWorkbook(OPCPackage.open(in));
					// List<XSSFPictureData>
					// pictures=wb.getAllPictures();
				} else {
					System.out.println("不支持");
					return null;
				}
			}

			Cell cell1 = null;
			Cell cell2 = null;
			String cellValue1,cellValue2;

			Sheet st = wb.getSheetAt(0);
			List<List<String>> array = new ArrayList<List<String>>();

			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				Row row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}

				int tempRowSize = row.getLastCellNum() + 1;
				
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				//String[] values = new String[rowSize];

				//Arrays.fill(values, "");

				boolean hasValue = false;
				
				cell1 = row.getCell(0);
				cell2 = row.getCell(1);
				//如果没有这两个单元格，跳过
				if(cell1 == null && cell2 == null) {
					continue;
				}
				cellValue1 = getStringCellValue(cell1);
				cellValue2 = getStringCellValue(cell2);
				//如果第二列没有数据，跳过
				if(StringUtils.isBlank(cellValue2)) {
					continue;
				}
				List<String> rowValues = new ArrayList<String>();//声明这一行的数据
				rowValues.add(cellValue1);//加入第一列数据
				rowValues.add(cellValue2);//加入第二列数据
				
				array.add(rowValues);
			}

			in.close();

			return array;
		} else {
			return null;
		}
	}
	
	public static String[][] getDataForCity(HttpServletRequest request, int ignoreRows)
			throws IOException, InvalidFormatException {
		if (request instanceof MultipartHttpServletRequest) {
			List<String[]> result = new ArrayList<String[]>();

			int rowSize = 0;

			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			MultipartFile mf = multiRequest.getFile("excel");

			InputStream in = mf.getInputStream();

			Workbook wb = null;

			// 打开HSSFWorkbook
			/*
			 * POIFSFileSystem fs = new POIFSFileSystem(in);
			 */

			if (in.available() > 0) {
				if (!in.markSupported()) {
					in = new PushbackInputStream(in, 8);
				}
				if (POIFSFileSystem.hasPOIFSHeader(in)) {// 2003
					wb = new HSSFWorkbook(in);
					// List<HSSFPictureData>
					// pictures=wb.getAllPictures();
				} else if (POIXMLDocument.hasOOXMLHeader(in)) {// 2007
					wb = new XSSFWorkbook(OPCPackage.open(in));
					// List<XSSFPictureData>
					// pictures=wb.getAllPictures();
				} else {
					System.out.println("不支持");
					return null;
				}
			}

			Cell cell = null;
			/*
			 * HSSFSheet st1 = wb.getSheetAt(0);
			 * 
			 * HSSFRow row1 = st1.getRow(ignoreRows - 2);
			 * 
			 * cell = row1.getCell(0);
			 * 
			 * String classify = cell.getStringCellValue();
			 */
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				Sheet st = wb.getSheetAt(sheetIndex);
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					Row row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					String[] values = new String[rowSize];
					Arrays.fill(values, "");
					boolean hasValue = false;
					for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									if (date != null) {
										value = new SimpleDateFormat("yyyy-MM-dd").format(date);
									} else {
										value = "";
									}
								} else {
									value = new DecimalFormat("0").format(cell.getNumericCellValue());
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								// 导入时如果为公式生成的数据则无值
								if (!cell.getStringCellValue().equals("")) {
									value = cell.getStringCellValue();
								} else {
									value = cell.getNumericCellValue() + "";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y" : "N");
								break;
							default:
								value = "";
							}
						}
						/*if ((columnIndex == 0 && value.trim().equals("")) || (columnIndex == 1 && value.trim().equals("")) || (columnIndex == 2 && value.trim().equals(""))) {

							break;

						}*/
						values[columnIndex] = rightTrim(value);
						hasValue = true;
					}
					if (hasValue) {
						//if(!StringUtils.isBlank(values[0]) || !StringUtils.isBlank(values[1]) || !StringUtils.isBlank(values[2]) || !StringUtils.isBlank(values[3]) || !StringUtils.isBlank(values[4])) {
						result.add(values);
						
					}
				}
			}
			in.close();
			String[][] returnArray = new String[result.size()][rowSize];
			for (int i = 0; i < returnArray.length; i++) {
				returnArray[i] = (String[]) result.get(i);
			}
			return returnArray;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	public static String getStringCellValue(Cell cell) {
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf((int) cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}

		return strCell;
	}
	
	public String getCellValue(HSSFCell cell) {
		String value = null;
		//注意：一定要设成这个，否则可能会出现乱码
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = "";
				}
			} else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			// 导入时如果为公式生成的数据则无值
			if (!cell.getStringCellValue().equals("")) {
				value = cell.getStringCellValue();
			} else {
				value = cell.getNumericCellValue() + "";
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() == true ? "Y": "N");
			break;
		default:
			value = "";
		}
		return value;
	}

	public static HSSFDataValidation createListBox(String[] list, Integer row,
			Integer col) {
		DVConstraint constraint = DVConstraint
				.createExplicitListConstraint(list);
		CellRangeAddressList regions = new CellRangeAddressList(row, row, col,
				col);
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,
				constraint);
		return data_validation;
	}

}
