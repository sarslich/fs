package com.ken.module.api.validator;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ken.core.aggregate.JsonResult;
import com.ken.core.util.StringUtils;
import com.ken.module.api.ApiConstant;
import com.ken.module.api.ApiCode;
import com.ken.module.api.ApiFactory;

@Service
public class SystemParamValidator  implements Validator{

	private static Logger logger = LoggerFactory.getLogger(SystemParamValidator.class);
	
	public JsonResult validate(Map<String,Object> params){
		JsonResult result = new JsonResult();
		//校验appkey
		if(!params.containsKey(ApiConstant.PARAM_APPKEY)){
			result.setCode(ApiCode.APPKEY_MISS.getCode());
			result.setMessage(ApiCode.APPKEY_MISS.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}else if(StringUtils.isBlank(params.get(ApiConstant.PARAM_APPKEY).toString())){
			result.setCode(ApiCode.APPKEY_ERROR.getCode());
			result.setMessage(ApiCode.APPKEY_ERROR.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}
		
		//校验method
		if(!params.containsKey(ApiConstant.PARAM_METHOD)){
			result.setCode(ApiCode.METHOD_PARAM_MISS.getCode());
			result.setMessage(ApiCode.METHOD_PARAM_MISS.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}else if(StringUtils.isBlank(params.get(ApiConstant.PARAM_METHOD).toString())){
			result.setCode(ApiCode.METHOD_PARAM_ERROR.getCode());
			result.setMessage(ApiCode.METHOD_PARAM_ERROR.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}else if(!ApiFactory.getServiceMap().containsKey(params.get(ApiConstant.PARAM_METHOD))){
			result.setCode(ApiCode.METHOD_NOT_USABLE.getCode());
			result.setMessage(ApiCode.METHOD_NOT_USABLE.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}
		
		//校验v
		if(!params.containsKey(ApiConstant.PARAM_V)){
			result.setCode(ApiCode.VERSION_MISS.getCode());
			result.setMessage(ApiCode.VERSION_MISS.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}else if(StringUtils.isBlank(params.get(ApiConstant.PARAM_V).toString())){
			result.setCode(ApiCode.VERSION_ERROR.getCode());
			result.setMessage(ApiCode.VERSION_ERROR.getValue());
			logger.debug("validate systemParams fail "+result.getResultJson());
			return result;
		}
		
		//校验sig
		String val = "0";
				//Param.dao.cacheGet(SysConstant.VAS_CHECK_SIG).getStr("val");
		if("0".equals(val)){//0：需要校验SIG参数，其他值不需要校验
			if(!params.containsKey(ApiConstant.PARAM_SIG)){
				result.setCode(ApiCode.SIG_PARAM_MISS.getCode());
				result.setMessage(ApiCode.SIG_PARAM_MISS.getValue());
				logger.debug("validate systemParams fail "+result.getResultJson());
				return result;
			}else if(StringUtils.isBlank(params.get(ApiConstant.PARAM_SIG).toString())){
				result.setCode(ApiCode.SIG_PARAM_ERROR.getCode());
				result.setMessage(ApiCode.SIG_PARAM_ERROR.getValue());
				logger.debug("validate systemParams fail "+result.getResultJson());
				return result;
			}
		}
		result.setSuccess(true);
		return result;
	}

}
