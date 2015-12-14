package com.ken.core.tools;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 公共工具类
 * @author 董华健  2012-9-7 下午2:20:06
 */
public class ToolUtils {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ToolUtils.class);
	
	/**
	 * double精度调整
	 * @param doubleValue 需要调整的值123.454
	 * @param format 目标样式".##"
	 * @return
	 */
	public static String decimalFormatToString(double doubleValue, String format){
		DecimalFormat myFormatter = new DecimalFormat(format);  
		String formatValue = myFormatter.format(doubleValue);
		return formatValue;
	}
	
	/**
	 * 获取UUID by jdk
	 * @author 董华健    2012-9-7 下午2:22:18
	 * @return
	 */
	public static String getUuidByJdk(boolean is32bit){
		String uuid = UUID.randomUUID().toString();
		if(is32bit){
			return uuid.toString().replace("-", ""); 
		}
		return uuid;
	}
	
	/**
	 * 获取订ID
	 * @param lg
	 * @return
	 */
	public static String getOrderId(int lg){
		String uuid = getUuidByJdk(true);
		return uuid.substring(0, lg);
	}
	
	/**
	 * 判断 字符串、Map、List是否为空
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isBlank(Object obj){
		if(obj==null){
			return true;
		}else if(obj instanceof String){
			return StringUtils.isBlank(((String)obj));
		}else if(obj instanceof Map){
			return ((Map)obj).isEmpty();
		}if(obj instanceof List){
			return ((List)obj).isEmpty();
		}else{
			return false;
		}
	}
	
	public static void main(String[] args){
		System.out.println(getUuidByJdk(true));
	}
	
}
