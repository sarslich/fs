package com.ken.module.api.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ken.core.aggregate.JsonResult;
import com.ken.core.tools.ToolString;
import com.ken.core.tools.ToolUtils;
import com.ken.core.tools.ToolWeb;
import com.ken.module.api.APIErrorConstant;
import com.ken.module.api.ApiConstant;
import com.ken.module.api.action.APIAction;
import com.ken.module.customer.CustomerService;
import com.ken.module.customer.entity.Customer;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ApiInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7938699149035944167L;
	private static Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);
	@Resource
	private CustomerService customerService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 取得请求相关的ActionContext实例		 
		ActionContext ctx = invocation.getInvocationContext();
		//TODO：write to db;
		//InterfaceLog log = new InterfaceLog();
		HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
		String url = ToolWeb.getRequestURIWithParam( request );
		JsonResult result = null;
		//标准接口
		if(url.endsWith("/order")){
			Map<String,Object> params = findRequestParam(request);
			Customer customer = customerService.cacheGet(String.valueOf(params.get("appkey")));
			if (customer == null){
				result = new JsonResult(APIErrorConstant.ERROR_APPKEY_INVALIDA,false);
				result.setMessage(APIErrorConstant.ERROR_APPKEY_INVALIDA_MSG);
				APIAction action = (APIAction)invocation.getAction();
				action.setResult(result);

				return "success";
			}
//			log.set("reqParam", JSONObject.toJSONString(params));			
//			if(c!=null){
//				log.set("operator", c.get("customerName"));
//			}
			request.setAttribute("requestParam", params);
		}
		return invocation.invoke();
	}

	/**
	 * 获取请求参数
	 * @return
	 */
	private Map<String,Object> findRequestParam(HttpServletRequest request){
		Map<String,Object> params = new HashMap<String, Object>();
		findFromEnumer(params, request);
		//如果是POST请求，如果在parameter中没取到数据从流中取数据
		if(params.isEmpty() && ApiConstant.HTTP_POST.equalsIgnoreCase(request.getMethod())){
			String paramData = findParamFromStream(ApiConstant.CHARSET_UTF8,request);
			if(paramData.length()>0){
				//JSON格式数据
				strToMap(paramData,params);
			}
		}
		logger.info(JSONObject.toJSON(params).toString());
		return params;
	}
	
	private void strToMap(String str,Map<String,Object> params){
		if(str.matches("\\{(.*):(.*)\\}")){
			JSONObject json = JSONObject.parseObject(str);
			for (String key : json.keySet()) {
				params.put(key, json.get(key));
			}
		}else{//name-value
			params.putAll(ToolString.convertParams(str));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void findFromEnumer(Map<String,Object> params,HttpServletRequest request){
		Enumeration<String> enumer =  request.getParameterNames();
		String name;
		while(enumer.hasMoreElements()){
			name = enumer.nextElement();
			if(!ToolUtils.isBlank(request.getParameter(name))){
				params.put(name, request.getParameter(name));
			}
		}
		logger.info("findFromEnumer1======"+JSONObject.toJSON(params).toString());
		if(params.isEmpty()){
			enumer = request.getParameterNames();
			while(enumer.hasMoreElements()){
				strToMap(enumer.nextElement(), params);
				logger.info("findFromEnumer2======"+JSONObject.toJSON(params).toString());
				return;
			}
		}
	}

	/**
	 * 从流中获取参数
	 * @param params
	 */
	private String findParamFromStream(String chaset,HttpServletRequest request){
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			request.setCharacterEncoding(chaset);
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(), chaset));
			String line = "";
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		logger.info("findParamFromStream======"+buffer.toString());
		return buffer.toString();
	}
}
