package com.ken.core.repository;

import java.io.Serializable;
import java.util.List;

import com.ken.core.aggregate.Page;
import com.ken.core.repository.support.ModelSetup;




/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-24 上午11:47:14 
 
 * @Description: 数据库操作接口
*/ 
public interface IRepository<T> {
    T get(Serializable id);

    T findUnique(ModelSetup ModelSetup);
    
    List<T> getAll();

    List<T> getAll(ModelSetup ModelSetup);
    
    void update(Object o);

    void create(Object o);

    void remove(Object o);

    void removeById(Serializable id);

    /**
     * 获取Entity对象的主键名.
     * @param clazz
     * @return
     */
    String getIdName(Class clazz);

    Integer getCount(ModelSetup ModelSetup);
    
    Boolean isUnique(ModelSetup ModelSetup);

    Page pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize);
}
