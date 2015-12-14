package com.ken.core.exception.source;


/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-21 下午4:47:06 
 
 * @Description: 标明异常来源归属
*/ 
public enum ExceptionSource {
    GLOBAL,//全局
    WEB,//页面级
    BIZ,//业务级
    DB ,//数据库级
    SYS //第三方系统级
}
