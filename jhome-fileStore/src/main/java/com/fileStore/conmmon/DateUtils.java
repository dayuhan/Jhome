package com.fileStore.conmmon;
/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @program: jhome-root
 * @description:
 * @author: Daxv
 * @create: 2020-09-12 13:09
 **/

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 *dateUtils修改为线程安全类DateTimeFormatter，
 * ●Instant——它代表的是时间戳
 * ●LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日，入职日期等。
 * ●LocalTime——它代表的是不含日期的时间
 * ●LocalDateTime——它包含了日期及时间，不过还是没有偏移信息或者说时区。
 * ●ZonedDateTime——这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。
 */
public class DateUtils {

    private static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    private static final String YYYYMMDD="yyyyMMdd";
    private static final String YYYYMMDD2="yyyy-MM-dd";
    private static final String HHMMSS="HHmmss";
    private static final String MMddHHMMSS="MMddHHmmssSSS";
    private static final String YYYYMMDDHHMMSS="yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSS2="yyyy-MM-dd HH:mm:ss";
    private static final String YYYYMM="yyyy/MM";

    private static final DateTimeFormatter formatter10 = DateTimeFormatter.ofPattern(TIMESTAMP);
    private static final DateTimeFormatter formatter11 = DateTimeFormatter.ofPattern(YYYYMMDD);
    private static final DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern(HHMMSS);
    private static final DateTimeFormatter formatter13 = DateTimeFormatter.ofPattern(MMddHHMMSS);
    private static final DateTimeFormatter formatter14 = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS);
    private static final DateTimeFormatter formatter15 = DateTimeFormatter.ofPattern(YYYYMMDDHHMMSS2);
    private static final DateTimeFormatter formatter16 = DateTimeFormatter.ofPattern(YYYYMM);
    private static final DateTimeFormatter formatter17 = DateTimeFormatter.ofPattern(YYYYMMDD2);
    /**
     * 返回时间戳格式 yyyyMMddHHmmss
     * @return
     */
    public static String getyyyyMMddHHmmss(){
        return LocalDateTime.now().format(formatter14);
    }

    /**
     * 返回时间戳格式 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getyyyyMMddHHmmss2(){
        return LocalDateTime.now().format(formatter15);
    }


    /**
     *获取当前时间往后多少分钟的UTC时间戳
     * @param minute
     * @return
     */
    public static long dateToStamp(long minute) {
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(minute);
        Timestamp timestamp = Timestamp.valueOf(ldt);
        return timestamp.getTime()/1000;
    }

    /**
     * 日期路径 即年/月 如2018/08
     */
    public static String datePath() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getYear() + File.separator + localDateTime.getMonth().getValue();
    }

    /**
     * 格式化日期 即年-月-日 如2018-08-01
     */
    public static LocalDate getDate(String dateTime) {
        return LocalDate.parse(dateTime, formatter17);
    }

    /**
     * 获取unix时间戳
     * @return
     */
    public static long getUnixTimestamp() {
        //初始化时区对象，北京时间是UTC+8，所以入参为8
        ZoneOffset zoneOffset= ZoneOffset.ofHours(8);
        //初始化LocalDateTime对象
        LocalDateTime localDateTime=LocalDateTime.now();
        //获取LocalDateTime对象对应时区的Unix时间戳
        return localDateTime.toEpochSecond(zoneOffset);
    }
    public static void main(String[] args) {
//        String format1 = formatter15.format(LocalDateTime.now());
//        System.out.println(format1);
//        System.out.println(formatter11.format(LocalDateTime.now()));
//
//
//        Instant ins=Instant.now();
//        System.out.println(ins);
//
//        OffsetDateTime time=ins.atOffset(ZoneOffset.ofHours(8));
//        System.out.println(formatter15.format(time));
        System.out.println(LocalDate.parse("2020-01-01", formatter17));

    }

}
