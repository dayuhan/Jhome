package com.bracket.common.BatchExcel;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.Notify.Notify;
import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.BatchExcel.Notify.Notify;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.List;


/**
 * @param <T>
 * @author xu.da1 执行引擎
 */
public class RowToEntity<T> {
    private T entity;

    public RowToEntity() {
        super();
        // this.entity=entity.newInstance();
    }

    public T ToEntity(Row row, List<ExcelteColumnMapping> columnMappings,
                      FunctionRegistry functionRegistry, VerifyEngine verifyEngine,
                      StringBuilder sbBuilder, Notify notify, Class<T> entityClass) {
        try {
            entity = entityClass.newInstance();
        } catch (Exception e) {
            // TODO: handle exception
            sbBuilder.append(String.format("实例化泛型对象失败，失败信息：", e.getMessage()));
            notify.NotifyHandel(String.format("实例化泛型对象失败，失败信息：", e.getMessage()));
        }
        for (ExcelteColumnMapping columnMapping : columnMappings) {
            try {
                String functionName = "";
                Field field = GetProperties(entity, columnMapping);
                String type = field.getGenericType().toString();
                // 用户自定义转换
                if (columnMapping.getExcelColumnName().isEmpty() && columnMapping.getExcelColumnName() != "") {
                    functionName = columnMapping.getExcelColumnName();
                } else {
                    // 系统默认转换
                    if (columnMapping.getConvertFunctionName() != null && type.equals("class java.util.Date")) {
                        functionName = FunctionType.DateTimeConverter.toString();
                    } else if (columnMapping.getConvertFunctionName() != null && type.equals("class java.lang.Boolean")) {
                        functionName = FunctionType.YesOrNoConverter.toString();
                    } else if (columnMapping.getConvertFunctionName() != null && type.equals("class java.lang.Double")) {
                        functionName = FunctionType.DoubleConverter.toString();
                    } else if (columnMapping.getConvertFunctionName() != null && type.equals("class java.lang.Integer")) {
                        functionName = FunctionType.IntConverter.toString();
                    } else if (columnMapping.getConvertFunctionName() != null && type.equals("class java.lang.Long")) {
                        functionName = FunctionType.IntConverter.toString();
                    } else {
                        functionName = FunctionType.StringConverte.toString();
                    }
                }
                ConverterDao cDao = functionRegistry.getEventHandler().get(functionName);
                String value = String.valueOf(GetCellValue(row, columnMapping.getExcelColumnName()));
                Object[] arg = new Object[2];
                arg[0] = row;
                arg[1] = value;
                // 验证字段
                // 转换格式
                Object obj = Invoke(cDao, arg);
                field.set(entity, obj);

            } catch (Exception e) {
                sbBuilder.append(String.format("发生错误  行: %s号  列：%s 异常信息：%s",
                        row.getRowNum(), columnMapping.getExcelColumnName(),
                        e.getMessage()));
                notify.NotifyHandel(String.format("发生错误  行: %s号  列：%s 异常信息：%s",
                        row.getRowNum(), columnMapping.getExcelColumnName(),
                        e.getMessage()));
                e.printStackTrace();

            }
        }
        return entity;
    }

    public Object Invoke(ConverterDao cDao, Object[] arg) throws ParseException {

        if (arg == null)
            return null;
        return cDao.converterFunc((Row) arg[0], arg[1]);
        /*
         * if(cDao.getClass().getSimpleName().equals("DateTimeConverterImpl")) {
         * cDao.getClass().getMethod("dateTimeConverterFunc", (Class<?>[])
         * arg1[0]); }
         * if(cDao.getClass().getSimpleName().equals("DoubleConverterImpl")) {
         * cDao.getClass().getMethod("doubleConverterFunc", (Class<?>[])
         * arg1[0]); }
         * if(cDao.getClass().getSimpleName().equals("IntConverterImpl")) {
         * cDao.getClass().getMethod("doubleConverterFunc", (Class<?>[])
         * arg1[0]); }
         * if(cDao.getClass().getSimpleName().equals("SexConverterImpl")) { }
         * if(cDao.getClass().getSimpleName().equals("StringConverteImpl")) { }
         * if(cDao.getClass().getSimpleName().equals("YesOrNoConverterImpl")) {
         * }
         */

    }

    /**
     * 反射列名字
     *
     * @param obj
     * @param columnMapping
     * @return
     */
    public Field GetProperties(T obj, ExcelteColumnMapping columnMapping) {

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {

            if (columnMapping.getEntityColumnName().toLowerCase()
                    .equals(field.getName().toLowerCase())) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取泛型对象
     *
     * @return
     */
    public T getInstanceOfT() {

        // TODO Auto-generated constructor stub
        ParameterizedType ptype = (ParameterizedType) getClass()
                .getGenericInterfaces()[0];
        Class c = (Class) ptype.getActualTypeArguments()[0];
        try {
            return (T) c.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

        /*
         * Type[] type = this.getClass().getGenericInterfaces();
         *
         * Class<?> c = (Class) type[0];
         *
         * // Type[] ptype = this.getClass().getGenericInterfaces(); // Class
         * clazz = (Class) ptype[0];
         *
         *
         * ParameterizedType ptype = (ParameterizedType)
         * this.getClass().getGenericSuperclass(); Class clazz = (Class<T>)
         * ptype.getActualTypeArguments()[0]; try { return (T)
         * clazz.newInstance(); } catch (Exception e) { // Oops, no default
         * constructor throw new RuntimeException(e); }
         */
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
        if (cell != null) {
            cell.setCellType(cell.CELL_TYPE_STRING);
        }
        return cell == null ? null : cell.getStringCellValue();
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

}
