package com.account.common.sbUtil.webstat.filter;

import com.account.common.sbUtil.webstat.entity.StatCtrRetCodeEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

//@Component
//@WebFilter(filterName = "WebCtrFilter", urlPatterns = "/**")
public class WebCtrFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(WebCtrFilter.class);

    private Map<String, StatCtrRetCodeEntity> tempResultBuffer = new ConcurrentHashMap<String, StatCtrRetCodeEntity>();
    private Map<String, StatCtrRetCodeEntity> totalResultBuffer = new ConcurrentHashMap<String, StatCtrRetCodeEntity>();

    private long lastSendTime = System.currentTimeMillis() / 1000;
    static List<String> specialURI = new ArrayList<String>();

    static {
        specialURI.add("/jkq/teammember");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        log.error("WebCtrFilter init...");
    }

    public List<StatCtrRetCodeEntity> getTempResult() {
        List<StatCtrRetCodeEntity> list = new ArrayList<StatCtrRetCodeEntity>();
        for(Map.Entry<String, StatCtrRetCodeEntity> entry : tempResultBuffer.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    public List<StatCtrRetCodeEntity> totalTempResult(){
        List<StatCtrRetCodeEntity> list = new ArrayList<StatCtrRetCodeEntity>();
        for(Map.Entry<String, StatCtrRetCodeEntity> entry : totalResultBuffer.entrySet()){
            list.add(entry.getValue());
        }
        return list;
    }

    /*方法二：推荐，速度最快
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private String parseURI(String uri) {
        for (String str : specialURI) {
            if (uri.startsWith(str)) {
                return str;
            }
        }

        String vec[] = uri.split("/");
        String ret = "";
        for (String str : vec) {
            if (str.equals("")) {
                continue;
            }

            if (isInteger(str) == true) {
                ret += "/" + "*";
            } else {
                ret += "/" + str;
            }
        }
        return ret;
    }

    private void addStat(Map<String, StatCtrRetCodeEntity> resultBuffer, String uri, long elapseTime, Integer returnCode) {
        StatCtrRetCodeEntity resultBufferItem = null;
        if (resultBuffer.containsKey(uri) == true) {
            resultBufferItem = resultBuffer.get(uri);
        } else {
            resultBufferItem = new StatCtrRetCodeEntity();
            resultBuffer.put(uri, resultBufferItem);
        }
        resultBufferItem.uri = uri;
        resultBufferItem.totalElapseTime += elapseTime;
        if (elapseTime > resultBufferItem.maxElapseTime) {
            resultBufferItem.maxElapseTime = elapseTime;
        }

        Integer eclapseTimeValue = 10;
        if (elapseTime <= 10) {
            eclapseTimeValue = 10;
        } else if (elapseTime > 10 && elapseTime <= 100) {
            eclapseTimeValue = 100;
        } else if (elapseTime > 100 && elapseTime <= 500) {
            eclapseTimeValue = 500;
        } else if (elapseTime > 500 && elapseTime <= 1000) {
            eclapseTimeValue = 1000;
        } else if (elapseTime > 1000 && elapseTime <= 1500) {
            eclapseTimeValue = 1500;
        } else if (elapseTime > 1500 && elapseTime <= 2000) {
            eclapseTimeValue = 2000;
        } else if (elapseTime > 2000 && elapseTime <= 5000) {
            eclapseTimeValue = 5000;
        } else {
            eclapseTimeValue = 10000;
        }
        if (resultBufferItem.elapseTimeLevel.containsKey(eclapseTimeValue)) {
            Integer cnt = resultBufferItem.elapseTimeLevel.get(eclapseTimeValue);
            resultBufferItem.elapseTimeLevel.put(eclapseTimeValue, cnt + 1);
        } else {
            resultBufferItem.elapseTimeLevel.put(eclapseTimeValue, 1);
        }

        if (resultBufferItem.codeList.containsKey(returnCode)) {
            Integer cnt = resultBufferItem.codeList.get(returnCode);
            resultBufferItem.codeList.put(returnCode, cnt + 1);
        } else {
            resultBufferItem.codeList.put(returnCode, 1);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        log.error("WebCtrFilter doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //设置开始时间
        request.setAttribute("req_time", System.currentTimeMillis());

        //处理请求
        ResponseWrapper wrapResponse = new ResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(request, wrapResponse);
        byte[] data = wrapResponse.getResponseData();
        ServletOutputStream out = servletResponse.getOutputStream();
        out.write(data);

        //返回数据处理
        long elapseTime = 0;
        JSONObject json = null;
        String uri = null;
        try {
            if (request.getMethod().toUpperCase().equals("OPTIONS") == true) {
                return;
            }

            elapseTime = System.currentTimeMillis() - (Long) request.getAttribute("req_time");
            uri = parseURI(request.getRequestURI());
            String str = new String(data, "UTF-8");
            json = (JSONObject) JSONObject.parse(str);
        } catch (Exception ex) {
            return;
        }
        //返回码统计
        Integer returnCode = null;
        try {
            returnCode = json.getIntValue("returnCode");
        } catch (Exception ex) {
            return;
        }

//        log.error("WebCtrFilter returnCode: {}", returnCode);
        //临时数据统计（1分钟)
        addStat(tempResultBuffer, uri, elapseTime, returnCode);
        //所有数据统计（生命周期内1分钟)
        addStat(totalResultBuffer, uri, elapseTime, returnCode);
        long nowTime = System.currentTimeMillis() / 1000;
        if ((nowTime - lastSendTime) > 60) {

//            for (StatCtrRetCodeEntity value : resultBuffer.values()) {
//                //StatUtils.SendBaseStatData(value);
//            }
            tempResultBuffer.clear();
            lastSendTime = nowTime;
        }

    }

    @Override
    public void destroy() {
//        log.error("destroy...");
    }
}
