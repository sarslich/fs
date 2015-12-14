package com.ken.module.order;

import java.sql.Timestamp;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ken.core.aggregate.JsonResult;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.tools.ToolUtils;
import com.ken.core.util.StringUtils;
import com.ken.module.accountlog.entity.AccountLog;
import com.ken.module.accountlog.repository.AccountLogRepository;
import com.ken.module.common.SysConstant;
import com.ken.module.customer.CustomerService;
import com.ken.module.customer.entity.Customer;
import com.ken.module.order.entity.Order;
import com.ken.module.order.repository.OrderRepository;
import com.ken.module.product.entity.Product;
import com.ken.module.queue.BatchOrderExecutor;
import com.ken.module.queue.InvokeExecutor;
import com.ken.module.queue.OrderExecutor;

@Service
public class OrderService {
	private static Logger log = LoggerFactory.getLogger(OrderService.class);

	@Resource
	private CustomerService customerService;
	@Resource
	private OrderRepository dao;
	@Resource
	private AccountLogRepository accountLogRepository;
	@Resource
	private BackwardInvoke invoke;
	

	@Transactional
	public void doOrder(String orderId){
		try {
			log.info("--------------ids="+orderId);
			Order order = dao.get(orderId);
			if(order==null){
				return;
			}
			log.info("--------------order="+order);
			
			Customer customer = null;
			boolean isStop = false;
			boolean isDeduct = false;
			synchronized (this) {
				customer = customerService.cacheGet(order.getCustomerId());
				//余额不足,暂停开通业务短信（一天发一次）
				isStop =  isStopAmount(order, customer);
				if(!isStop){
					//扣除金额并保存扣除记录
					isDeduct =  deductAmount(order, customer);
					if(!isDeduct){
						log.info("-------------扣款失败-------------");
						order.setMessage( "扣除余额失败,终止开通.");
					}
				}else{
					order.setMessage( "账户余额不足,开通失败.");
//					SmsModelService.service.sendStopSms(order, customer);
				}
			}
			if(isStop || !isDeduct){
//				order.setStatus( OrderEnum.ORDER_STATUS_8.getCode());
				dealFail(customer.getCustomerName(),order);
				return;
			}
			
			// HTTP请求接口基，开通产品
			JSONObject jsonData =  invoke.fillIsmOrder(order);
			jsonData.put("customerName", customer.getCustomerName());
			
			JsonResult result = invoke.invokeIsmOrder(SysConstant.BACKWARD_INVOKE_URL,jsonData);
			if (result.isSuccess()) {
				JSONObject data = JSONObject.parseObject(result.getContent().toString());
				if (SysConstant.GUOXIN_RESULT_SUCCESS_CODE.equals(data.getString(SysConstant.RETURN_CODE))) {
					//开通成功，更新订单状态					 
					order.setMessage( OrderEnum.ORDER_STATUS_7.getValue());
					dealSuccess(order, customer);
				//订单处理中
				} else if(SysConstant.GUOXIN_RESULT_DAELLING_CODE.equals(data.getString(SysConstant.RETURN_CODE))){
					order.setMessage( OrderEnum.ORDER_STATUS_1.getValue());
					 dealOpening(order, customer);
				}else{
					//开通不成功，更新订单状态
					order.setMessage( "调用接口订购失败，code: "+data.getString(SysConstant.RETURN_CODE)+", message:"+data.getString(SysConstant.RETURN_MSG));
//					order.setStatus( changeCode(data.getString("status")));
					dealFail(order,customer);
				}
			}else{
				//开通不成功，更新订单状态(处理异步订购接口开通响应超时的，直接更新为订购中)
				if("400".equals(result.getCode())){//响应超时
					order.setMessage( "响应超时,更新状态为订购中");
					dealOpening(order, customer);
				}else{
//					order.setStatus(OrderEnum.ORDER_STATUS_8.getCode());
					order.setMessage( result.getMessage());
					dealFail(order,customer);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("订购失败",e);
		}

	}
		
	/**
	 * 生成订单并保存在队列中等待处理
	 * @param params
	 * @param product
	 * @return
	 */
	public JsonResult createOrder(Map<String,Object> params,Product product){
		JsonResult result = new JsonResult();
		Customer customer = null;
		if(!ToolUtils.isBlank(params.get("appkey"))){
			customer = customerService.cacheGet(params.get("appkey").toString());
		}else{
//			customer = customerDao.cacheGetById(params.get("customerId").toString());
		}		

		Order order = new Order();		 
		order.setOrderFrom((String)params.get("orderFrom"));
		order.setProductCode(product.getProdCode());
		order.setProdId(product.getId());
		order.setPhoneNo((String)params.get("phoneNo"));
		order.setCustomerId(customer.getId());
	 
		 
		order.setCallbackUrl((String)params.get("callbackUrl"));
		order.setOrderTime(new Timestamp(System.currentTimeMillis() ));	
		order.setProvince( product.getProvince());	 
		order.setChannel( product.getChannelId());
		order.setProdName(product.getProdName());		 
		order.setProductSize(product.getProductSize());
		order.setOperator(product.getOperator());
		order.setStatus(OrderEnum.ORDER_STATUS_0.getCode());
		order.setMessage(OrderEnum.ORDER_STATUS_0.getValue());
		order.setOrderPrice(product.getPrice());
		
		String orderId = ToolUtils.getOrderId(30);
		order.setId(orderId);
		
		//流量产品需要给客户算折扣
		if(ToolUtils.isBlank(product.getProvince())){//国内产品
			setSellPriceAndDiscount(order, customer, product.getPrice(), "0");
		}else{//省内产品
			setSellPriceAndDiscount(order, customer, product.getPrice(), "1");
		}
		//保存订单信息
		try{			 
			if(ToolUtils.isBlank(params.get("batchId"))){//单个订购		
				order.setBatchId(null);
			}else{
				order.setBatchId((String)params.get("batchId"));
			}
 
			dao.create(order);
			
			JSONObject json = new JSONObject();
			//将订单放到队列中
			if(ToolUtils.isBlank(params.get("batchId"))){//单个订购				 
				OrderExecutor.setOrder(orderId);
			}else{//批量订购
				BatchOrderExecutor.setOrder(orderId);
			}
			result.setCode(OrderEnum.ORDER_STATUS_0.getCode());
			result.setContent(json);
			result.setMessage(OrderEnum.ORDER_STATUS_0.getValue());
			result.setSuccess(true);
			json.put(SysConstant.RETURN_ORDER_ID, orderId);
		}catch(Exception e){
			result.setCode(OrderEnum.ORDER_STATUS_2.getCode());
			result.setMessage(OrderEnum.ORDER_STATUS_2.getValue());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
		
	}

 
	/**
	 * 设置销售价格和折扣
	 */
	private void setSellPriceAndDiscount(Order order,Customer customer,Double vasProdPrice,String region){
		if("0".equals(region)){//国内产品
			if("1".equals(order.getOperator())){//国内电信
				if(customer.getGnTeleDiscount()!=null){
					order.setCustomerDiscount(customer.getGnTeleDiscount());
					order.setSellPrice(vasProdPrice.doubleValue()*customer.getGnTeleDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}else if("2".equals(order.getOperator())){//国内移动
				if(customer.getGnCmccDiscount()!=null){
					order.setCustomerDiscount( customer.getGnCmccDiscount());
					order.setSellPrice( vasProdPrice.doubleValue()*customer.getGnCmccDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}else if("3".equals(order.getOperator())){//国内联通
				if(customer.getGnUnicomDiscount()!=null){
					order.setCustomerDiscount(  customer.getGnUnicomDiscount());
					order.setSellPrice( vasProdPrice.doubleValue()*customer.getGnUnicomDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}
		}else{//省内产品
			if("1".equals(order.getOperator())){//省内电信
				if(customer.getSnTeleDiscount()!=null){
					order.setCustomerDiscount(  customer.getSnTeleDiscount());
					order.setSellPrice( vasProdPrice.doubleValue()*customer.getSnTeleDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}else if("2".equals(order.getOperator())){//省内移动
				if(customer.getSnCmccDiscount()!=null){
					order.setCustomerDiscount(  customer.getSnCmccDiscount());
					order.setSellPrice( vasProdPrice.doubleValue()*customer.getSnCmccDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}else if("3".equals(order.getOperator())){//省内联通
				if(customer.getSnUnicomDiscount()!=null){
					order.setCustomerDiscount( customer.getSnUnicomDiscount());
					order.setSellPrice( vasProdPrice.doubleValue()*customer.getSnUnicomDiscount().doubleValue()/100);
				}else{
					order.setSellPrice( vasProdPrice);
				}
			}
		}
	}
	
	/**
	 * 检查客户金额是否达到暂停业务
	 * @param order
	 * @param customer
	 * TODO:赠送金额暂未考虑
	 * @return
	 */
	public boolean isStopAmount(Order order,Customer customer){
		//充值余额
		Double amount = ToolUtils.isBlank( customer.getAmount())?Double.valueOf(0):customer.getAmount();
		//赠送余额
//		Double giveAmount = "null".equals(String.valueOf(customer.get))?BigDecimal.valueOf(0):customer.getBigDecimal("giveAmount");
		//信用剩余额度
		Double creditAmount = ToolUtils.isBlank( customer.getCreditAmount())?Double.valueOf(0):customer.getCreditAmount();
		if(creditAmount.doubleValue()>0){//有信用额度
			if((amount.doubleValue()+creditAmount.doubleValue()) <  order.getSellPrice().doubleValue() ){//充值余额+赠送余额+信用额度<开通产品价格  开通失败
				return true;
			}
		}else{
			if(amount.doubleValue() < order.getSellPrice()){//充值余额+赠送余额<开通产品价格  开通失败
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 扣除金额并保存扣除记录
	 * @param order
	 * @param customer
	 * @param alog
	 * TODO:  先扣客户赠送金额，赠送金额不够再扣账户余额，账户余额不够再扣信用额度
	 * 目前没有处理赠送金额的问题
	 * @return
	 */
	private boolean deductAmount(Order order,Customer customer){
				
		Double sellPrice = order.getSellPrice();
		Double amount = ToolUtils.isBlank( customer.getAmount())?Double.valueOf(0):customer.getAmount();
		
		Double creditAmount = ToolUtils.isBlank( customer.getCreditAmount())?Double.valueOf(0):customer.getCreditAmount();
		//记录账户扣款记录
		AccountLog alog = new AccountLog();
		alog.setCustomerId(customer.getId());
		alog.setOrderId( order.getId());
		alog.setCreateTime(new Timestamp(System.currentTimeMillis() ));
		alog.setType("0");//扣款
		
		if((amount+creditAmount)>sellPrice){
			alog.setAmount( sellPrice);
		}else{
			return false;
		}
		
//		if(sellPrice.compareTo(giveAmount)>=0){//赠送金额-订单金额>=0
//			BigDecimal sub = sellPrice.subtract(giveAmount);
//			
//			alog.set("alterGive", giveAmount);
//			if(amount.add(creditAmount).compareTo(sub)>=0){
//				alog.set("alterAmount", sub);
//			}else{
//				return false;
//			}
//		}else{
//			alog.set("alterGive", sellPrice);
//			alog.set("alterAmount", new BigDecimal(0));
//		}
		accountLogRepository.create(alog);
		//更新客户账户余额
		Double _amount = 0 - alog.getAmount();

		log.info("--------------扣费金额：amount="+_amount);
		customerService.updateAmount(customer.getId(), _amount);
		return true;
	}	
	
	/**
	 * 处理失败订单(不需要给客户退款的)
	 * @param order
	 * @param customer
	 * @return
	 */
	public void dealFail(String customerName,Order order){
//		String changCode = order.getStatus();
		order.setStatus(OrderEnum.ORDER_STATUS_8.getCode());
		order.setOpenTime(new Timestamp(System.currentTimeMillis() ));
		//更新订单状态失败
		dao.update(order);
//		if(!order.update()){
//			return false;
//		}
		
//		order.setStatus( changCode);
		//回调客户接口更新订购状态
		String url = order.getCallbackUrl();
		if(!StringUtils.isBlank(url)){
			JSONObject jsonData =  fillCustomerOrder(order);
			jsonData.put("callbackUrl", url);
//			jsonData.put("customerName", customerName);
			InvokeExecutor.setOrder(jsonData.toJSONString());
		}
		log.debug("产品Code="+order.getProductCode()+"开通失败.");
//		return true;
	}
	
	/**
	 * 处理失败订单(异步订购接口，需要给客户退款的)
	 * @param order
	 * @param customer
	 * @return
	 */
	public void dealFail(Order order,Customer customer){
		
//		String changCode = order.getStatus();
		order.setStatus(OrderEnum.ORDER_STATUS_8.getCode());
		order.setOpenTime(new Timestamp(System.currentTimeMillis() ));
		//更新订单状态失败
		dao.update(order);
//		if(!order.update()){
//			return false;
//		}
//		
//		order.setStatus( changCode);
		//开通失败，给客户退款
		//获取扣款记录
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("orderId", order.getId());
		modelSetup.addParameter("type", "0");
		modelSetup.setSqlName("findAlog");	
		AccountLog alog = accountLogRepository.findUnique(modelSetup);
		if(alog==null){//已经退款或者没有扣款记录
			return ;
		}
		//保存退款记录
		alog.setType( "1");
		alog.setCreateTime(new Timestamp(System.currentTimeMillis() ));
		accountLogRepository.create(alog);
		log.info("--------------退款金额：giveAmount="+alog.getAmount());
		//更新客户账户余额
		customerService.updateAmount(customer.getId(), alog.getAmount());
		
		//发送短信通知用户开通失败
//		SmsModelService.service.sendUserSms(SysConstant.SMS_TEMP_4, order);
				
		//回调客户接口更新订购状态
		String url = order.getCallbackUrl();
		if(!StringUtils.isBlank(url)){
			JSONObject jsonData = invoke.fillCustomerOrder(order);
			jsonData.put("callbackUrl", url);
//			jsonData.put("customerName", customer.getCustomerName());
			InvokeExecutor.setOrder(jsonData.toJSONString());
		}
				
		log.debug("产品Code="+order.getProductCode()+"开通失败.");
//		return true;
	}
	
	/**
	 * 回调客户接口报文
	 * @param order
	 * @return
	 */
	private JSONObject fillCustomerOrder(Order order){
		JSONObject json = new JSONObject();
		json.put(SysConstant.RETURN_CODE, order.getStatus());
		json.put(SysConstant.RETURN_MSG, order.getMessage());
		JSONObject item = new JSONObject();
		item.put(SysConstant.RETURN_ORDER_ID, order.getId());
		json.put(SysConstant.RETURN_DATA, item);
		return json;
	}
	
	/**
	 * 处理成功订单
	 * @param order
	 * @param customer
	 * @return
	 */
	public void dealSuccess(Order order,Customer customer){
		order.setStatus(OrderEnum.ORDER_STATUS_7.getCode());
		order.setOpenTime( new Timestamp(System.currentTimeMillis() ));
		
		//更新更新订单为订购成功
		dao.update(order);
//		if(!order.update()){
//			return false;
//		}
				
		//开通成功发送短信通知用户
//		SmsModelService.service.sendUserSms(SysConstant.SMS_TEMP_3, order);
		
		//检查客户金额是否达到预警额并且发送提示短信
//		SmsModelService.service.sendWarnSms(order, customer);
		
		//回调客户接口更新订购状态
		String url = order.getCallbackUrl();
		if(!StringUtils.isBlank(url)){
			JSONObject jsonData = invoke.fillCustomerOrder(order);
			jsonData.put("callbackUrl", url);
//			jsonData.put("customerName", customer.getCustomerName());
			InvokeExecutor.setOrder(jsonData.toJSONString());
		}
		log.debug("产品Code="+order.getProductCode()+"开通成功.");
//		return true;
	}
	
	/**
	 * 处理开通中订单
	 * @param order
	 * @param customer
	 * @return
	 * 
	 */
	public void dealOpening(Order order,Customer customer){
		order.setStatus(OrderEnum.ORDER_STATUS_1.getCode());
		
		//更新处理开通中订单
		dao.update(order);
//		if(!order.update()){
//			return false;
//		}
				
		//发送短信通知用户已受理
//		SmsModelService.service.sendUserSms(SysConstant.SMS_TEMP_2, order);
		
		//检查客户金额是否达到预警额并且发送提示短信
//		SmsModelService.service.sendWarnSms(order, customer);
		
		//回调客户接口更新订购状态
		String url = order.getCallbackUrl();
		if(!StringUtils.isBlank(url)){
			JSONObject jsonData = invoke.fillCustomerOrder(order);
			jsonData.put("callbackUrl", url);
//			jsonData.put("customerName", customer.getStr("customerName"));
			InvokeExecutor.setOrder(jsonData.toJSONString());
		}
//		return true;
	}
}
