package com.bracket.common.BatchExcel.FuncImpl;

import com.bracket.common.BatchExcel.ConvertFunctionDao.ConverterDao;
import com.bracket.common.ToolKit.StringUtil;
import org.apache.poi.ss.usermodel.Row;

import java.text.ParseException;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @program: jhome-root
 * @description:
 * @author: Daxv
 * @create: 2021-05-13 16:23
 **/
public class LongConverterImpl implements ConverterDao {
    @Override
    public Object converterFunc(Row row, Object object) throws ParseException {
        try {
            if (StringUtil.isBlank(((String) object).trim()) || object.equals("null"))
                return 0;
            return Long.valueOf((String) object);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
