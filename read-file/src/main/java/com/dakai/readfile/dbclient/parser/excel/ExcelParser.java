package com.dakai.readfile.dbclient.parser.excel;

import com.dakai.readfile.dbclient.parser.RowData;
import com.dakai.readfile.dbclient.parser.SourceData;
import com.dakai.readfile.dbclient.parser.SourceParser;
import com.dakai.readfile.dbclient.parser.TypeHandler;
import com.dakai.readfile.dbclient.utils.StringUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelParser implements SourceParser {

	@Override
	public List<RowData> parse(InputStream inputStream, int ignore) throws Exception {
		SourceData data = parseAndCheck(inputStream, null, ignore);
		return data.getAllList();
	}

	@Override
	public List<RowData> parse(String sourcePath, int ignore) throws Exception {
		InputStream fs = inputStream(sourcePath);
		return parse(fs, ignore);
	}

	@Override
	public List<String[]> parse2(InputStream inputStream, int ignore) throws Exception {
		SourceData data = parseAndCheck(inputStream, null, ignore);
		return data.getAllStringList();
	}

	@Override
	public List<String[]> parse2(String sourcePath, int ignore) throws Exception {
		InputStream fs = inputStream(sourcePath);
		return parse2(fs, ignore);
	}

	@Override
	public SourceData parseAndCheck(InputStream inputStream, Map<Integer, TypeHandler> typeHandlers, int ignore) throws Exception {
		Workbook workbook = WorkbookFactory.create(inputStream);
		SourceData result = new SourceData();
		foreachRow(workbook, result, typeHandlers, ignore);
		return result;
	}

	@Override
	public SourceData parseAndCheck(String sourcePath, Map<Integer, TypeHandler> typeHandlers, int ignore) throws Exception {
		InputStream fs = inputStream(sourcePath);
		return parseAndCheck(fs, typeHandlers, ignore);
	}

	private InputStream inputStream(String path) throws FileNotFoundException {
		return new FileInputStream(path);
	}

	private void foreachRow(Workbook workbook, SourceData result, Map<Integer, TypeHandler> typeHandlers, int ignore) {
		if(workbook != null) {
			for(int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum ++) {
				//????????????sheet?????????
				Sheet sheet = workbook.getSheetAt(sheetNum);
				if(sheet == null){
					continue;
				}
				//????????????sheet????????????
				int firstRowNum  = sheet.getFirstRowNum()+1;
				//????????????sheet????????????
				int lastRowNum = sheet.getLastRowNum();
				if(ignore > 0) {
					firstRowNum = ignore;
				}
				if(ignore > lastRowNum) {
					return;
				}
				//?????????????????????????????????
				for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
					//???????????????
					Row row = sheet.getRow(rowNum);
					if(row == null){
						continue;
					}
					//???????????????????????????
					int firstCellNum = row.getFirstCellNum();
					//????????????????????????
					int lastCellNum = row.getPhysicalNumberOfCells();
					Object[] cells = new Object[lastCellNum];
					List<Object> normalCells = new ArrayList<Object>();
					String[] stringCells = new String[lastCellNum];
					int normal = 1;  // 1????????????????????? 2 ??????????????????
					int hasValue = 0;  //
					StringBuilder errorSB = new StringBuilder();
					//???????????????
					for(int cellNum = firstCellNum; cellNum<lastCellNum;cellNum++){
						TypeHandler handler = null;
						if(typeHandlers != null) {
							handler = typeHandlers.get(cellNum+1);
						}
						Cell cell = row.getCell(cellNum);
						HandlerResult cellResult = getCellValue(cell, handler);
						cells[cellNum] = cellResult.value;
						stringCells[cellNum] = cellResult.originValue;
						if(cellResult.status == 2) {
							normal = 2;
							errorSB.append(cellNum + ": " + cellResult.errorInfo + ";");
						}
						if(handler != null) {
							normalCells.add(cellResult.value);
						}
						if(StringUtil.isBlank(cellResult.originValue)) {
							hasValue++;
						}
					}
					if(hasValue < (lastCellNum - firstCellNum)) {
						result.putAll(new RowData(rowNum+1, cells), stringCells);
						if(normal == 1) {
							result.putNormal(new RowData(rowNum+1, normalCells.toArray()), stringCells);
						}else if(normal == 2) {
							result.putAbnormal(cells, errorSB.toString(), stringCells, rowNum+1);
						}
					}
				}
			}
		}
	}

	private HandlerResult getCellValue(Cell cell, TypeHandler handler) {
		Object cellValue = "";
		if(cell == null){
			return new HandlerResult(1, cellValue);
		}
		String errorInfo = null;
		//?????????????????????
		switch (cell.getCellType()){
			case Cell.CELL_TYPE_NUMERIC: //??????
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
					Date date = cell.getDateCellValue();
					if(date != null){
						cellValue = new SimpleDateFormat("yyyy-MM-dd").format(date);
					}
				}else {
					cellValue = cell.getNumericCellValue();
				}
				break;
			case Cell.CELL_TYPE_STRING: //?????????
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN: //Boolean
				cellValue = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA: //??????
				cellValue = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_BLANK: //??????
				cellValue = "";
				break;
			case Cell.CELL_TYPE_ERROR: //??????
				cellValue = "????????????";
				errorInfo = "????????????";
				break;
			default:
				cellValue = "????????????";
				errorInfo = "????????????";
				break;
		}
		HandlerResult result = new HandlerResult(1, cellValue);
		result.originValue = String.valueOf(cellValue);
		if(StringUtil.isBlank(errorInfo)) {
			if(handler != null) {
				try {
					result.value = handler.handle(cellValue);
				} catch (Exception e) {
					result.value = cellValue;
					result.status = 2;
					result.errorInfo = "??????????????????";
				}
			}
		}else {
			result.value = "";
			result.status = 2;
			result.errorInfo = errorInfo;
		}
		return result;
	}

	private class HandlerResult{
		int status;
		Object value;
		String originValue;
		String errorInfo;
		public HandlerResult(int status, Object value) {
			super();
			this.status = status;
			this.value = value;
		}
	}

}
