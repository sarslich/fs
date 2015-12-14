package com.ken.module.api.validator;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ken.core.aggregate.JsonResult;

public interface Validator {
	/**
	 * 校验入口
	 * @param params
	 * @return
	 */
	public JsonResult validate(Map<String,Object> params);
	
	
}
