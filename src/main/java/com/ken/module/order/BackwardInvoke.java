package com.ken.module.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ken.core.aggregate.JsonResult;
import com.ken.core.http.HTTPClient;
import com.ken.core.tools.ToolSecuritySHA;
import com.ken.core.tools.ToolUtils;
import com.ken.module.common.SysConstant;
import com.ken.module.order.entity.Order;


/**
 * 后向流量远程调度器
 * @author YangJiang
 * @time   2015年9月20日 下午5:31:35
 */
@Service
public class BackwardInvoke extends HTTPClient{
		
	/**
	 * 调用接口基订购产品
	 * @param url
	 * @param jsonData
	 * @return
	 */
	public JsonResult invokeIsmOrder(String url,JSONObject jsonData){
		JsonResult result = doPost(false,url, jsonData.toJSONString(), null);
		if(!result.isSuccess()){
			result.setMessage("请求接口基订购接口失败,"+result.getMessage());
		}
		return result;
	}
	

	/**
	 * 回调客户接口更新订购状态
	 * @return
	 */
	public JsonResult invokeCustomerOrder(String url,JSONObject jsonData){
		JsonResult result = doPost(false,url, jsonData.toJSONString(), null);
		if(!result.isSuccess()){
			result.setMessage("回调客户订购接口失败,"+result.getMessage());
		}
		return result;
	}
	
	/**
	 * 调用接口基订单状态
	 * @param url
	 * @param jsonData
	 * @return
	 */
	public JsonResult invokeIsmOrderStatus(String url,JSONObject jsonData){
		JsonResult result = doPost(false,url, jsonData.toJSONString(), null);
		if(!result.isSuccess()){
			result.setMessage("请求接口基获取订单状态接口失败,"+result.getMessage());
		}
		return result;
	}
	
	
//------------------------------------------------------------组装接口报文-------------------------------------------------------
	/**
	 * 订购接口报文
	 * @return
	 */
	public JSONObject fillIsmOrder(Order order){
		//TODO:目前只有国信			
		Map<String,Object> data = new HashMap<String,Object>();
    	data.put("appkey", SysConstant.GUOXIN_APPKEY);
    	data.put("method", "backward.order");
    	data.put("v", "1");
    	 
    	data.put("productId", order.getPhoneNo());
		data.put("phoneNo",  order.getProductCode());		 
		data.put("callbackUrl", SysConstant.BACKWARD_INVOKE_URL);
		
		String pStr = ToolSecuritySHA.dataDecrypt(data);
		String sig = ToolSecuritySHA.shaEncrypt(pStr+SysConstant.GUOXIN_SECURITY_APPKEY);
		data.put("sig",sig);	        
        
		JSONObject json = (JSONObject)JSONObject.toJSON(data);  
	 
		return json; 
	}
	
	/**
	 * 回调客户接口报文
	 * @param order
	 * @return
	 */
	public JSONObject fillCustomerOrder(Order order){
		JSONObject json = new JSONObject();
		json.put(SysConstant.RETURN_CODE, order.getSellPrice());
		json.put(SysConstant.RETURN_MSG, order.getMessage());
		JSONObject item = new JSONObject();
		item.put(SysConstant.RETURN_ORDER_ID, order.getId());
		json.put(SysConstant.RETURN_DATA, item);
		return json;
	}
	
	/**
	 * 获取订单状态报文
	 * @param order
	 * @return
	 */
	public JSONObject ismOrderStatusRequest(Order order){
		JSONObject reqpara = new JSONObject();
		reqpara.put("method", "BackwardFlow"+order.getChannel()+".queryOrderStatus");
		JSONObject reqjson = new JSONObject();
		reqjson.put("requestNo", order.getId());
		JSONObject json = new JSONObject();
		json.put("reqpara", reqpara);
		json.put("reqjson", reqjson);
		return json;
	}
}
