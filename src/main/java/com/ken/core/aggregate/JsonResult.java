package com.ken.core.aggregate;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.ken.module.api.ApiConstant;

public class JsonResult implements Serializable{

	private static final long serialVersionUID = 2277149614655301707L;
	
	private String code;     //结果代码
	
	private String message;  //结果描述
	
	private Object content;  //结果数据
	
	private boolean success; //成功标识
	
	public JsonResult(){}
	
	public JsonResult(String code,boolean success){
		this.code = code;
		this.success = success;
	}
	
	public JsonResult(boolean success){
		this.success = success;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public JSONObject getResultJson(){
		JSONObject json = new JSONObject();
		json.put(ApiConstant.RETURN_CODE, code);
		json.put(ApiConstant.RETURN_MSG, message);
		if(success && content!=null){
			json.put(ApiConstant.RETURN_DATA, JSONObject.toJSON(content));
		}
//		return json.toJSONString();、
		return json;
	}
	
	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public static void main(String[] args) {
		System.out.println(JSONObject.toJSONString("content"));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
