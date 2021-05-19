package com.bracket.common.BatchExcel.FuncImpl;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.ToolKit.StringUtil;
import org.apache.poi.ss.usermodel.Row;

import java.text.SimpleDateFormat;
import java.util.Date;


public class YesOrNoConverterImpl implements ConverterDao {

    public Object converterFunc(Row row, Object object) {
        // TODO Auto-generated method stub
        try {
            if (StringUtil.isBlank(((String) object).trim()) || object.equals("null"))
                return 0;
            return Boolean.valueOf((String) object);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }


}
