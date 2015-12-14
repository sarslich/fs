package com.ken.module.product;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ken.core.aggregate.JsonResult;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.tools.ToolUtils;
import com.ken.module.api.ApiCode;
import com.ken.module.phone.PhoneHeadService;
import com.ken.module.phone.entity.PhoneHead;
import com.ken.module.product.entity.Product;
import com.ken.module.product.repository.ProductRepository;

@Service
public class ProductService {

	@Resource
	private ProductRepository dao;
	@Resource
	private PhoneHeadService phoneHeadService;
	
	private static Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	public JsonResult filtFlow(Map<String,Object> params){
		JsonResult result = new JsonResult();
		//获取号段信息
		//TODO PhoneHead phone = PhoneHead.dao.findFist(params.get("phoneNo").toString());
		String phonehead = params.get("phoneNo").toString().substring(0,7);
		PhoneHead phone = phoneHeadService.findTempFist(phonehead);
		if(phone==null){
			result.setCode(ApiCode.ISM_CODE_28.getCode());
			result.setMessage(ApiCode.ISM_CODE_28.getValue());
			logger.debug("filter product fail "+result.getResultJson());
			return result;
		}
		
		//手机号码运营商为空
		params.put("operator", phone.getOperator());
		if(ToolUtils.isBlank(params.get("operator"))){
			result.setCode(ApiCode.BUSINESS_PARAM_ERROR.getCode());
			result.setMessage("手机号码运营商为空");
			logger.debug("filter product fail "+result.getResultJson());
			return result;
		}
		
		//判断手机号归属省份为空
		if(ToolUtils.isBlank(phone.getProvince())){
			result.setCode(ApiCode.BUSINESS_PARAM_ERROR.getCode());
			result.setMessage("没有找到手机号码归属省份");
			logger.debug("filter product fail "+result.getResultJson());
			return result;
		}
		
		params.put("province", phone.getProvince());
		
//		if(ToolUtils.isBlank(params.get("ruleType"))&&ToolUtils.isBlank(params.get("productId"))){
//			result.setCode(ApiCode.BUSINESS_PARAM_ERROR.getCode());
//			result.setMessage("选择产品规则和产品ID必须选择一个");
//			logger.debug("filter product fail "+result.getResultJson());
//			return result;
//		}
		//获取产品信息
		Product product = findProduct(params.get("productCode").toString());
		if (product==null){
			result.setCode(ApiCode.NOT_PRODUCT.getCode());
			result.setMessage(ApiCode.NOT_PRODUCT.getValue());
			logger.debug("filter product fail "+result.getResultJson());
			return result;
		}
		if (product.getProvince()!=null && !product.getProvince().equals(phone.getProvince())){
			result.setCode(ApiCode.NOT_PRODUCT.getCode());
			result.setMessage(ApiCode.NOT_PRODUCT.getValue());
			logger.debug("filter product fail "+result.getResultJson());
			return result;
		}
 
		result.setContent(product);
		result.setSuccess(true);
		return result;
	}
	
	
	public Product findProduct(String productCode){
		IBatisModelSetup  modelSetup = new IBatisModelSetup();
		modelSetup.addParameter("productCode", productCode);
		modelSetup.setSqlName("findProduct");	
		
		Product product = dao.findUnique(modelSetup);
		return product;
	}
}
