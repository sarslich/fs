package com.ken.core.entity;

import java.io.Serializable;

/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-6-11 下午12:38:08 
 
 * @Description: domian entity的基类
*/ 
public abstract class Entity<T> implements Serializable{

	private static final long serialVersionUID = -5183833816286050102L;
	/**
	 * 统一的ID声明
	 * 
	 */
    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
