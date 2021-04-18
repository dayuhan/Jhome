package com.ar.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 签名工具类
 *
 * @author xzl
 * @ClassName: SignUtils
 * @Description: TODO
 * @date 2019年12月12日
 */
public class SignUtils {

    /**
     * 签名算法
     *
     * @param @param  sign
     * @param @param  signContent
     * @param @return
     * @return boolean
     * @throws
     * @Title: checkSign
     * @Description: TODO
     */
    public static String sign(SortedMap<Object, Object> packageParams, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);

        //算出摘要
        return MD5Util.encodeMD5Hex(sb.toString()).toLowerCase();
    }

    /**
     * 参数签名校验
     *
     * @param @param  sign
     * @param @param  signContent
     * @param @return
     * @return boolean
     * @throws
     * @Title: checkSign
     * @Description: TODO
     */
    public static boolean checkSign(SortedMap<Object, Object> packageParams, String key) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (!"sign".equals(k) && null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);

        //算出摘要
        String mysign = MD5Util.encodeMD5Hex(sb.toString()).toLowerCase();
        String sign = ((String) packageParams.get("sign")).toLowerCase();

        return sign.equals(mysign);
    }
}
