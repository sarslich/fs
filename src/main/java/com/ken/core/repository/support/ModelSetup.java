package com.ken.core.repository.support;

import java.util.Map;


/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-24 上午11:50:39 
 
 * @Description: 构建数据库操作时所用的数据对象
*/ 
public interface ModelSetup {

     public void setup(Map<String,Object> params);


}
