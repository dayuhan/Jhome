package com.account.common.sbUtil.webstat.entity;

import java.io.Serializable;
import java.util.HashMap;

public class StatCtrRetCodeEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String uri;
    public long maxElapseTime = 0;
    public long totalElapseTime = 0;
    public HashMap<Integer, Integer> elapseTimeLevel = new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> codeList = new HashMap<Integer, Integer>();

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public long getMaxElapseTime() {
        return maxElapseTime;
    }
    public void setMaxElapseTime(long maxElapseTime) {
        this.maxElapseTime = maxElapseTime;
    }
    public long getTotalElapseTime() {
        return totalElapseTime;
    }
    public void setTotalElapseTime(long totalElapseTime) {
        this.totalElapseTime = totalElapseTime;
    }
    public HashMap<Integer, Integer> getElapseTimeLevel() {
        return elapseTimeLevel;
    }
    public void setElapseTimeLevel(HashMap<Integer, Integer> elapseTimeLevel) {
        this.elapseTimeLevel = elapseTimeLevel;
    }
    public HashMap<Integer, Integer> getCodeList() {
        return codeList;
    }
    public void setCodeList(HashMap<Integer, Integer> codeList) {
        this.codeList = codeList;
    }
}
