package com.ken.module.api.action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ken.core.aggregate.JsonResult;
import com.ken.core.tools.ToolString;
import com.ken.core.web.action.GenericAction;
import com.ken.module.api.ApiCode;
import com.ken.module.api.ApiConstant;
import com.ken.module.api.ApiFactory;
import com.ken.module.api.validator.BusinessParamValidator;
import com.ken.module.api.validator.PermissionValidator;
import com.ken.module.api.validator.SystemParamValidator;
import com.ken.module.common.SysConstant;

@Namespace("/")
@ParentPackage("default")
public class APIAction extends GenericAction{

	private static final long serialVersionUID = -4105630844910063034L;
	
	@Resource
	private SystemParamValidator sysValidator;
	@Resource
	private PermissionValidator permissionValidator;
	@Resource
	private BusinessParamValidator businessValidator;
 

	private JsonResult result;
	private static Logger logger = LoggerFactory.getLogger(APIAction.class);
 
	//暂未用！
	//,interceptorRefs = {@InterceptorRef(value = "mydefault")} 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action( value = "/assets/order",  results = { @Result(name = "success", type = "json",params = {"root","result.resultJson"} )} )
	public String execute()  throws Exception {
 
		Map params =  getParameterMap(this.request);

	
		try {
			// 校验接口参数合法性
			result = sysValidator.validate(params);
			if (!result.isSuccess()) {
				return SUCCESS;
			}
			// 校验接口调用方是否有权限
			result = permissionValidator.validate(params);
			if (!result.isSuccess()) {
				return SUCCESS;
			}

			// 校验接口业务参数是否有效
			result = businessValidator.validate(params);
			if (!result.isSuccess()) {
				return SUCCESS;
			}

			// 设置开通渠道为接口开通
			params.put("orderFrom", SysConstant.ORDER_FROM_1);
			String methodName = params.get(ApiConstant.PARAM_METHOD).toString();
			// 获取业务接口实例
			Object obj =  ApiFactory.apiFactory.getApiService(methodName);			
	 
			// 适配调用的相应接口方法
			result = this.invoke(obj, removeSystemParam(params), methodName);

			logger.debug("\n------Invoke " + methodName + " ==> result " + result);
			return SUCCESS;

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();	
			result = new JsonResult();
			result.setCode(ApiCode.VAS_SYSTEM_ERROR.getCode());
			result.setMessage(ApiCode.VAS_SYSTEM_ERROR.getValue());
			return SUCCESS;
		} finally {
			 
		} 
	 
	}

//	@Action( value = "/assets/order",  results = { @Result(name = "noPermission", type = "json",params = {"root","json"} )} )
//	public String execute()  throws Exception {
// 
////		orderService.saveOrder(orderId);		
//
//		
//		json.put(ApiConstant.RETURN_CODE, "0");
//		json.put(ApiConstant.RETURN_MSG, "我很好");
//
//		
//		result = new APIResult("0",true);
//		result.setMessage("我很好");
//
//		System.out.println(result.getResultJson());
//		
//		
//		
//		return "noPermission";
//	}
	
 


	public JsonResult getResult() {
		return result;
	}


	public void setResult(JsonResult result2) {
		this.result = result2;
	}
 
 
	/**
	 * 清除系统参数
	 * @param params
	 * @return
	 */
	private Map<String,Object> removeSystemParam(Map<String,Object> params){
		if(!params.isEmpty()){
			//params.remove(ApiConstant.PARAM_APPKEY);
			params.remove(ApiConstant.PARAM_METHOD);
			params.remove(ApiConstant.PARAM_V);
			params.remove(ApiConstant.PARAM_PRIVATEKEY);
			params.remove(ApiConstant.PARAM_SIG);
		}
		return params;
	}
	
	/**
	 * 通过反射调用具体业务接口
	 * @param obj
	 * @param params
	 * @param methodName
	 * @return
	 */
	private JsonResult invoke(Object obj,Map<String,Object> params,String methodName) throws Exception{
		String md = ToolString.getMethod(methodName);
		Method method = obj.getClass().getMethod(md, Map.class);
		return (JsonResult)method.invoke(obj, params);
	}	

 
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map getParameterMap(HttpServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}


}
