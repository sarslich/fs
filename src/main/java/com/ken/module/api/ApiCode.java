package com.ken.module.api;

public enum ApiCode {
    ISM_SYSTEM_ERROR("-1","ISM系统异常,请联系管理员"),
	ISM_CODE_0("0","订单已受理"),
	ISM_CODE_1("1","订购中"),
	ISM_CODE_2("2","订单受理失败"),
	ISM_CODE_3("3","请求号码不合法"),
	ISM_CODE_4("4","请求格式不正确"),
	ISM_CODE_5("5","请求时间无效"),
	ISM_CODE_6("6","请求账户名丢失"),
	ISM_CODE_7("7","订购成功"),
	ISM_CODE_8("8","订购失败"),
	ISM_CODE_9("9","渠道商应用app信息丢失"),
	ISM_CODE_10("10","认证签名信息丢失"),
	ISM_CODE_11("11","订购码丢失"),
	ISM_CODE_12("12","无法查找到渠道应用关系"),
	ISM_CODE_13("13","认证签名失败"),
	ISM_CODE_14("14","解密参数失败"),
	ISM_CODE_15("15","应用鉴权失败"),
	ISM_CODE_16("16","批量订购（文件路径丢失）"),
	ISM_CODE_17("17","批量订购（文件不存在）"),
	ISM_CODE_18("18","批量订购（文件不合法）"),
	ISM_CODE_19("19","批量订购（文件重复提交）"),
	ISM_CODE_20("20","批量订购（orderNO不存在）"),
	ISM_CODE_21("21","查询失败"),
	ISM_CODE_22("22","该用户为4G号码，无法订购本地流量包"),
	ISM_CODE_23("23","请求type类型丢失"),
	ISM_CODE_24("24","查询订单号丢失，orderNO和userOrderNo必须有一个存在"),
	ISM_CODE_25("25","该订单号还在处理中，请稍候再查询!"),
	ISM_CODE_26("26","该订单号没有实际订购记录"),
	ISM_CODE_27("27"," 不支持的订购方式"),
	ISM_CODE_28("28"," 无法获取到号码信息"),
	ISM_CODE_29("29"," 请求账户无效"),
	ISM_CODE_30("30 ","渠道商信息丢失"),
	ISM_CODE_100("100","无法查找到渠道和产品匹配关系"),
	ISM_CODE_101("101","查询数据库异常"),
	ISM_CODE_102("102","产品库存不足"),
	ISM_CODE_103("103","服务器繁忙，请稍后在试"),
	ISM_CODE_104("104","无法查询到号码属性"),
	ISM_CODE_105("105","无法查找到合适的产品进行订购"),
	ISM_CODE_106("106","用户订购超出最大次数"),
	ISM_CODE_107("107","批量订购记录超过最大号码个数限制"),
	ISM_CODE_108("108","订购请求超时,请重新订购"),
	ISM_CODE_109("109","订购已受理"),
	
	SUCCESS("120","请求成功"),
	METHOD_PARAM_MISS("121","method参数缺失"),
	METHOD_PARAM_ERROR("122","method参数错误"),
	SIG_PARAM_MISS("123","sig参数缺失"),
	SIG_PARAM_ERROR("124","sig参数错误"),
	APPKEY_MISS("127","appkey参数缺失"),
	APPKEY_ERROR("128","appkey参数错误"),
	VERSION_MISS("129","版本参数缺失"),
	VERSION_ERROR("130","版本参数错误"),
	VAS_SYSTEM_ERROR("131","VAS系统错误,请联系管理员"),
	ILLEGAL_REQUEST("132","非法访问"),
	NOT_METHOD_PERMI("133","无该接口权限"),
	METHOD_NOT_USABLE("134","接口不可用"),
	USER_NOT_PERMI("135","用户无访问权限"),
	NOT_PRODUCT("136","没有获取到产品"),
	BUSINESS_PARAM_MISS("137","业务参数缺失"),
	USER_BALANCE_MIN("138","余额不足,请充值"),
	BUSINESS_PARAM_ERROR("139","业务参数错误"),
	ISM_CODE_666("666","订单处理中!");
	
	private String code;
	
	private String value;
	
	private ApiCode(String code,String value){
		this.code = code;
		this.value = value;
	}
	
	public String getValue(String code){
		ApiCode[] enums = ApiCode.values();
		for (ApiCode sysEnums : enums) {
			return sysEnums.getValue();
		}
		return "";
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
