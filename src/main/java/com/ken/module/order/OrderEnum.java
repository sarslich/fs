package com.ken.module.order;

 

public enum OrderEnum {


	ORDER_STATUS_0("0","订单受理中"),
	ORDER_STATUS_1("1","订购中"),
	ORDER_STATUS_2("2","订单受理失败"),
	ORDER_STATUS_7("7","订购成功"),
	ORDER_STATUS_8("8","订购失败"),
	ORDER_STATUS_9("9","未知状态"),
	ORDER_STATUS_665("-665","供应商系统返回状态码为空"),
	
	PRODUCT_TYPE_0("0","流量"),
	PRODUCT_TYPE_1("1","话费"),
	;
	
	private String code;
	
	private String value;
	
	private OrderEnum(String code,String value){
		this.code = code;
		this.value = value;
	}
	
	public String getValue(String code){
		OrderEnum[] enums = OrderEnum.values();
		for (OrderEnum sysEnums : enums) {
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
