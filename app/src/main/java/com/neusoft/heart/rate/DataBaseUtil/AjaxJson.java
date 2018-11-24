package com.neusoft.heart.rate.DataBaseUtil;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/*
 * 转javascript 对象为前台接收
 * */
public class AjaxJson {
	
	boolean success ;
	Object obj;//最大的父类
	Map<String,Object> attribute;
	String msg;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Map<String, Object> getAttribute() {
		return attribute;
	}
	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getJsonString(){
	JSONObject j=new JSONObject();
	j.put("success", this.success);
	j.put("msg", this.msg);
	j.put("obj", this.obj);
	j.put("attribute", this.attribute);
	return JSONObject.toJSONString(j);
	//System.out.print("s");
		
		
		
	}
	

}
