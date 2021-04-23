package com.bracket.common.BatchExcel;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.FuncImpl.*;
import com.bracket.common.BatchExcel.Notify.Notify;
import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.FuncImpl.*;
import com.bracket.common.BatchExcel.Notify.Notify;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xu.da1 转换引擎
 */
public class ExcelteEngine<T extends Object> {
	private Notify notify;//通知
	private RowToEntity<T> rowToEntity;
	private VerifyEngine verifyEngine;
	private FunctionRegistry functionRegistry;

	public ExcelteEngine(Notify notify) throws InstantiationException, IllegalAccessException {
		super();
		this.functionRegistry = new FunctionRegistry();
		this.verifyEngine = new VerifyEngine();
		this.rowToEntity = new RowToEntity<T>();

		// 注册转换函数
		this.functionRegistry.bind("DateTimeConverter",	new DateTimeConverterImpl());
		this.functionRegistry.bind("DecimalConverter",new DecimalConverterImpl());
		this.functionRegistry.bind("DoubleConverter",	new DoubleConverterImpl());
		this.functionRegistry.bind("IntConverter", new IntConverterImpl());
		this.functionRegistry.bind("SexConverter", new SexConverterImpl());
		this.functionRegistry.bind("StringConverte", new StringConverteImpl());
		this.functionRegistry.bind("YesOrNoConverter",	new YesOrNoConverterImpl()); 
		this.notify=notify;
		
	}

	/**
	 * @author xu.da1 转换器
	 * @param <T>
	 * @throws Exception
	 */
	public List<T> MappingToEntity(ExcelConfig config,
			List<ExcelteColumnMapping> columnMappings,StringBuilder sBuilder,Class<T> entityClass) throws Exception {
		if (!config.CheckRule()) {
			return null;
		}
		sBuilder.append(String.format("当前正在导入Sheet名：%s",config.getWorkSheet().getSheetName()));
		this.notify.NotifyHandel(String.format("当前正在导入Sheet名：%s",config.getWorkSheet().getSheetName()));
		List<T> entityList = new ArrayList<T>();
		XSSFSheet workSheet = config.getWorkSheet();
		int sheetRowEndIndex = workSheet.getLastRowNum();
		for (int i = config.getSheetRowStartIndex(); i < sheetRowEndIndex+1; i++) {
			Row row = workSheet.getRow(i);
			if (row == null) {
				continue;
			}
			// 为空终止导入
			if (isRowEmpty(row)) {
				break;
			}

			try {
				int nullcolumncount = 0; 
				for (ExcelteColumnMapping mapping : columnMappings) {
					Cell cell = (Cell) GetCellValue(row,mapping.getExcelColumnName());
					if (cell == null|| cell.getCellType() == Cell.CELL_TYPE_BLANK) {
						nullcolumncount++;
					}
				}
				if (nullcolumncount != columnMappings.size()) {
					T entity = rowToEntity.ToEntity(row, columnMappings,
							this.functionRegistry, this.verifyEngine,sBuilder,notify,entityClass);
              
                    
					entityList.add(entity);
				}
			} catch (Exception e) {
				sBuilder.append(String.format("发生错误 表名：%s 异常信息：%s", workSheet.getSheetName(),e.getMessage()));
				this.notify.NotifyHandel(String.format("发生错误 表名：%s 异常信息：%s", workSheet.getSheetName(),e.getMessage()));
				throw new Exception();
			}
		}
		return entityList;
	}

	/**
	 * 判断是否是空行
	 * 
	 * @param row
	 * @return
	 */
	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}

	/**
	 * 根据列名和行得到当前单元格
	 * 
	 * @param row
	 * @param coumnName
	 * @return
	 */
	public Object GetCellValue(Row row, String coumnName) {

		int index = ConvertExcelColumnNameToNumber(coumnName);
		Cell cell = row.getCell(index);	
		return cell == null ? null : cell;
	}

	public int ConvertExcelColumnNameToNumber(String columnName) {
		columnName = columnName.toUpperCase();
		// 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
		int count = -1;
		char[] cs = columnName.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			count += (cs[i] - 64) * (int) Math.pow(26, cs.length - 1 - i);
		}
		return count;
	}
	
	/**
	 * 绑定事件
	 * @param name
	 * @param conDao
	 */
	public void bindEventHandler(String name, ConverterDao conDao) {
		this.functionRegistry.bind(name,conDao);
	}

}
