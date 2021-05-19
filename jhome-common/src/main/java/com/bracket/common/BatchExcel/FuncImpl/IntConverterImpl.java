package com.bracket.common.BatchExcel.FuncImpl;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.ToolKit.StringUtil;
import org.apache.poi.ss.usermodel.Row;


public class IntConverterImpl implements ConverterDao {

    public Object converterFunc(Row row, Object object) {
        // TODO Auto-generated method stub
        try {
            if (StringUtil.isBlank(((String) object).trim()) || object.equals("null"))
                return Integer.valueOf(0);
            return Integer.valueOf(object.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return Integer.valueOf(0);
    }
}
