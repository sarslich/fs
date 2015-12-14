package com.ken.module.api.validator;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ken.core.aggregate.JsonResult;
import com.ken.core.tools.ToolSecuritySHA;
import com.ken.module.api.ApiConstant;
import com.ken.module.api.ApiCode;
import com.ken.module.custinterfaces.CustInterfacesService;
import com.ken.module.custinterfaces.entity.CustInterfaces;
import com.ken.module.customer.CustomerService;
import com.ken.module.customer.entity.Customer;
import com.ken.module.interfaces.InterfacesService;
import com.ken.module.interfaces.entity.Interfaces;

@Service
public class PermissionValidator implements Validator{

	private static Logger logger = LoggerFactory.getLogger(PermissionValidator.class);
	
	@Resource
	private CustomerService customerService;
	@Resource
	private InterfacesService interfacesService;
	@Resource
	private CustInterfacesService ctifService;
	
	public JsonResult validate(Map<String,Object> params){
		JsonResult result = new JsonResult();
		//校验appkey 没有接入系统或者是已被禁用
		Customer customer = customerService.cacheGet(params.get(ApiConstant.PARAM_APPKEY).toString());
		if(customer==null){
			result.setCode(ApiCode.ILLEGAL_REQUEST.getCode());
			result.setMessage(ApiCode.ILLEGAL_REQUEST.getValue());
			logger.debug("validate permission fail "+result.getResultJson());
			return result;
		}else if(ApiConstant.USER_DEL_FLAG.equals(customer.getDelFlag())){
			result.setCode(ApiCode.USER_NOT_PERMI.getCode());
			result.setMessage(ApiCode.USER_NOT_PERMI.getValue());
			logger.debug("validate permission fail "+result.getResultJson());
			return result;
		}
		Interfaces intf = interfacesService.cacheGet(params.get(ApiConstant.PARAM_METHOD).toString());
		if(intf==null){
			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
			logger.debug("validate permission fail "+result.getResultJson());
			return result;
		}else if(!ApiConstant.APP_USABLE.equals(intf.getIsUsable())){
			result.setCode(ApiCode.METHOD_NOT_USABLE.getCode());
			result.setMessage(ApiCode.METHOD_NOT_USABLE.getValue());
			logger.debug("validate permission fail "+result.getResultJson());
			return result;
		}
		CustInterfaces ci = ctifService.cacheGet(intf.getId(),customer.getId()); 
		if(ci==null){
			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
			logger.debug("validate permission fail "+result.getResultJson());
			return result;
		}
		//校验sig
		String val = "0";
				//Param.dao.cacheGet(SysConstant.VAS_CHECK_SIG).getStr("val");
		if("0".equals(val)){//0：需要校验SIG参数，其他值不需要校验
			String sig = params.get(ApiConstant.PARAM_SIG).toString();
			params.remove(ApiConstant.PARAM_SIG);
			String keyStr = ToolSecuritySHA.dataDecrypt(params)+customer.getSecurityKey();
			if(!ToolSecuritySHA.shaEncrypt(keyStr).equals(sig)){
				result.setCode(ApiCode.SIG_PARAM_ERROR.getCode());
				result.setMessage(ApiCode.SIG_PARAM_ERROR.getValue());
				logger.debug("validate permission fail "+result.getResultJson());
				return result;
			}
		}
		result.setSuccess(true);
		return result;
	}

//	public JsonResult validate(HttpServletRequest request){
//		JsonResult result = new JsonResult();
//		//校验appkey 没有接入系统或者是已被禁用
//		Customer customer = customerService.cacheGet(request.getParameter(ApiConstant.PARAM_APPKEY).toString());
//		if(customer==null){
//			result.setCode(ApiCode.ILLEGAL_REQUEST.getCode());
//			result.setMessage(ApiCode.ILLEGAL_REQUEST.getValue());
//			logger.debug("validate permission fail "+result.getResultJson());
//			return result;
//		}else if(ApiConstant.USER_DEL_FLAG.equals(customer.getDelFlag())){
//			result.setCode(ApiCode.USER_NOT_PERMI.getCode());
//			result.setMessage(ApiCode.USER_NOT_PERMI.getValue());
//			logger.debug("validate permission fail "+result.getResultJson());
//			return result;
//		}
//		Interfaces intf = interfacesService.cacheGet(request.getParameter(ApiConstant.PARAM_METHOD).toString());
//		if(intf==null){
//			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
//			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
//			logger.debug("validate permission fail "+result.getResultJson());
//			return result;
//		}else if(!ApiConstant.APP_USABLE.equals(intf.getIsUsable())){
//			result.setCode(ApiCode.METHOD_NOT_USABLE.getCode());
//			result.setMessage(ApiCode.METHOD_NOT_USABLE.getValue());
//			logger.debug("validate permission fail "+result.getResultJson());
//			return result;
//		}
//		CustInterfaces ci = ctifService.cacheGet(intf.getId(),customer.getId()); 
//		if(ci==null){
//			result.setCode(ApiCode.NOT_METHOD_PERMI.getCode());
//			result.setMessage(ApiCode.NOT_METHOD_PERMI.getValue());
//			logger.debug("validate permission fail "+result.getResultJson());
//			return result;
//		}
//		//校验sig
//		String val = "0";
//				//Param.dao.cacheGet(SysConstant.VAS_CHECK_SIG).getStr("val");
//		if("0".equals(val)){//0：需要校验SIG参数，其他值不需要校验
//			String sig = request.getParameter(ApiConstant.PARAM_SIG).toString();
//			request.getParameterMap().remove(ApiConstant.PARAM_SIG);
//			String keyStr = ToolSecuritySHA.dataDecrypt(params)+customer.getSecurityKey();
//			if(!ToolSecuritySHA.shaEncrypt(keyStr).equals(sig)){
//				result.setCode(ApiCode.SIG_PARAM_ERROR.getCode());
//				result.setMessage(ApiCode.SIG_PARAM_ERROR.getValue());
//				logger.debug("validate permission fail "+result.getResultJson());
//				return result;
//			}
//		}
//		result.setSuccess(true);
//		return result;
//	}
	
}
