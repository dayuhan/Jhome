package com.bracket.common.BatchExcel.FuncImpl;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import org.apache.poi.ss.usermodel.Row;


public class DoubleConverterImpl implements ConverterDao {
  
	public Object converterFunc(Row row, Object object) {
		// TODO Auto-generated method stub
		return Double.valueOf(object.toString());
	}
 

}
