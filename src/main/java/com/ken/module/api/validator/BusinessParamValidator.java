package com.ken.module.api.validator;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ken.core.aggregate.JsonResult;
import com.ken.module.api.ApiConstant;
import com.ken.module.api.ApiCode;
import com.ken.module.interfaces.InterfacesService;
import com.ken.module.interfaces.entity.Interfaces;

@Service
public class BusinessParamValidator implements Validator {

	private static Logger logger = LoggerFactory.getLogger(BusinessParamValidator.class);
			
	@Resource
	private InterfacesService interfacesService;
	
	public JsonResult validate(Map<String,Object> params){
		JsonResult result = new JsonResult();
		Interfaces intf = interfacesService.cacheGet(params.get(ApiConstant.PARAM_METHOD).toString());
		if(intf!=null){
			if(ApiConstant.APP_USABLE.equals(intf.getIsUsable())){
				if(intf.getParameter()!=null){
					String[] parameters = intf.getParameter().split(",");
					for (int i = 0; i < parameters.length; i++) {
						if(!params.containsKey(parameters[i])){
							result.setCode(ApiCode.BUSINESS_PARAM_MISS.getCode());
							result.setMessage("参数 "+parameters[i]+" 缺失");
							logger.debug("validate businessParams fail "+result.getResultJson());
							return result;
						}
					}
				}
			}else{
				result.setCode(ApiCode.METHOD_NOT_USABLE.getCode());
				result.setMessage(ApiCode.METHOD_NOT_USABLE.getValue());
				logger.debug("validate businessParams fail "+result.getResultJson());
				return result;
			}
		}else{
			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
			logger.debug("validate businessParams fail "+result.getResultJson());
			return result;
		}
		result.setSuccess(true);
		return result;
	}

//	public JsonResult validate(HttpServletRequest request){
//		JsonResult result = new JsonResult();
//		Interfaces intf = interfacesService.cacheGet(request.getParameter(ApiConstant.PARAM_METHOD).toString());
//		if(intf!=null){
//			if(ApiConstant.APP_USABLE.equals(intf.getIsUsable())){
//				if(intf.getParameter()!=null){
//					String[] parameters = intf.getParameter().split(",");
//					for (int i = 0; i < parameters.length; i++) {
//						if(request.getParameter(parameters[i])!=null){
//							result.setCode(ApiCode.BUSINESS_PARAM_MISS.getCode());
//							result.setMessage("参数 "+parameters[i]+" 缺失");
//							logger.debug("validate businessParams fail "+result.getResultJson());
//							return result;
//						}
//					}
//				}
//			}else{
//				result.setCode(ApiCode.METHOD_NOT_USABLE.getCode());
//				result.setMessage(ApiCode.METHOD_NOT_USABLE.getValue());
//				logger.debug("validate businessParams fail "+result.getResultJson());
//				return result;
//			}
//		}else{
//			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
//			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
//			logger.debug("validate businessParams fail "+result.getResultJson());
//			return result;
//		}
//		result.setSuccess(true);
//		return result;
//	}
	
}
