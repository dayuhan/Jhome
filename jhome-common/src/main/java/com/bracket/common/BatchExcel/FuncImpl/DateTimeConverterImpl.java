package com.bracket.common.BatchExcel.FuncImpl;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import org.apache.poi.ss.usermodel.Row;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public  class DateTimeConverterImpl implements ConverterDao {
  
	public Object converterFunc(Row row, Object object) throws ParseException {
		// TODO Auto-generated method stub
		if (object==null)
			return new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.parse(object.toString());
	}

 

}
