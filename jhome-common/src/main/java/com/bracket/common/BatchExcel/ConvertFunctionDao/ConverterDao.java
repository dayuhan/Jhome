package com.bracket.common.BatchExcel.ConvertFunctionDao;

import org.apache.poi.ss.usermodel.Row;

import java.text.ParseException;

/**
 * @author xu.da1
 * 用户自定义转换器接口
 */
public interface ConverterDao {
	 Object converterFunc(Row row, Object object) throws ParseException;
}
