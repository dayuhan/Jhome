package com.ar.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	// 启始角标
	private int offset;
	// 每页条数
	private int pageSize;
	//当前页数
	private  int pageNum;

	public Query(Map<String, Object> params) {
		this.putAll(params);
		// 分页参数
		if(!"".equals(params.get("pageSize")) && params.get("pageSize")!=null){
			this.pageSize = Integer.parseInt(params.get("pageSize").toString());
			if(!"".equals(params.get("pageNum")) && params.get("pageNum")!=null){
				this.offset = (Integer.parseInt(params.get("pageNum").toString())-1) * pageSize;
			}else{
				this.offset=0;
			}
		}else{
			this.pageSize=10;
			if(!"".equals(params.get("pageNum")) && params.get("pageNum")!=null){
				this.offset = (Integer.parseInt(params.get("pageNum").toString())-1) * pageSize;
			}else{
				this.offset=0;
			}
		}
		this.put("offset", offset);
		this.put("pageSize", pageSize);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getpageSize() {
		return pageSize;
	}

	public void setpageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getpageNum() {
		return pageNum;
	}

	public void setpageNum(int pageNum) {
		this.pageNum = pageNum;
	}
}
