package com.ken.module.api.adapter;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ken.core.aggregate.JsonResult;
import com.ken.module.order.OrderService;
import com.ken.module.product.ProductService;
import com.ken.module.product.entity.Product;



/**
 * 后向流量接口服务类
 * @author YangJiang
 * @time   2015年9月7日 下午2:46:18
 */
@Service
public class BackwardAdapter{
	
//	public static final BackwardAdapter adapter = new BackwardAdapter();
	
	@Resource
	private OrderService orderService;
	@Resource
	private ProductService productService;	
	
	/**
	 * 后向流量订购接口
	 * @return
	 */
	public JsonResult order(Map<String,Object> params){
		//根据规则获取产品
		JsonResult result = productService.filtFlow(params);		
		if(result.isSuccess()){
			Product product = (Product)result.getContent();
			//生成订单
			result = orderService.createOrder(params,product);
		}
		return result;
	}
	
	/**
	 * 查询用户信息
	 * @param params
	 * @return
	 */
//	public JsonResult findUserInfo(Map<String,Object> params){
//		JsonResult result = PhoneService.service.findUserInfo(params.get("phoneNo").toString());
//		if(result.isSuccess()){
//			PhoneHead phone = (PhoneHead)result.getContent();
//			JSONObject json = new JSONObject();
//			json.put("phoneNo", params.get("phoneNo"));
//			json.put("provinceNo", phone.get("province"));
//			json.put("provinceName", phone.get("provinceName"));
//			json.put("operator", phone.get("operator"));
//			result.setContent(json);
//		}
//		return result;
//	}
	
	/**
	 * 查询流量接口
	 * @return
	 */
	public JsonResult findOrders(Map<String,Object> params){
		
		return null;
	}
	
	/**
	 * 话费充值
	 * @param params
	 * @return
	 */
//	public JsonResult charge(Map<String,Object> params){
//		//根据规则获取产品
//		Result result = ProductService.service.filtCharge(params);
//		if(result.isSuccess()){
//	 		Product product = (Product)result.getContent();
//			//生成订单
//			result = OrderService.service.createOrder(params,product);
//		}
//		return result;
//	}
	
	
	
	/**
	 * 订单状态查询
	 * @param params
	 * @return
	 */
	public JsonResult orderStatus(Map<String,Object> params){
//		JsonResult result = new JsonResult();
//		Order order = OrderService.service.findById(params.get("orderId").toString());
//		JSONObject json = new JSONObject();
//		if(order!=null){
//			result.setSuccess(true);
//			json.put("orderId", order.getPKValue());
//			json.put("status", order.get("status"));
//			json.put("message", order.get("message"));
//			result.setContent(json);
//			result.setCode("0");
//			result.setMessage("查询成功");
//			result.setContent(json);
//		}else{
//			result.setCode(OrderCode.ISM_CODE_21.getCode());
//			result.setMessage(OrderCode.ISM_CODE_21.getValue()+",没有该订单信息");
//		}
//		return result;
		return null;
	}
	
	
	
	
	
	
//*******************************************************************************
	/**
	 * 更新订单信息
	 * @return
	 */
	public JsonResult updateOrder(Object orderId,Object status,Object message){
//		if(OrderCode.ISM_CODE_7.getCode().equals(status) || OrderCode.ISM_CODE_8.getCode().equals(status)){
//			return OrderService.service.updateOrder(orderId, status, message);
//		}
		return new JsonResult();
	}
}
