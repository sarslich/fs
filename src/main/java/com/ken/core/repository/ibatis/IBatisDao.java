package com.ken.core.repository.ibatis;

import java.io.Serializable;
import java.util.List;


import org.springframework.util.Assert;

import com.ken.core.aggregate.Page;
import com.ken.core.repository.IRepository;
import com.ken.core.repository.generic.IBatisGenericDao;
import com.ken.core.repository.support.ModelSetup;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.util.clazz.GenericsUtils;



/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-24 下午3:09:51 
 
 * @Description: ibatis 基础DAO，封装了常见的数据库操作；
*/ 
public abstract class IBatisDao<T> extends IBatisGenericDao implements IRepository<T> {
    private static final String INSERT = ".insert";
    private static final String UPDATE = ".update";
    private static final String DELETE = ".delete";
    private static final String DELETEBYID = ".deleteById";
    private static final String GET = ".get";
    private static final String GETALL = ".getAll";

    protected Class<T> entityClass;
    
    @SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
        if (entityClass == null)
            entityClass = (Class<T>) GenericsUtils.getSuperClassGenricType(getClass());
        return entityClass;
    }
    
    protected String getEntityName() {
        return getEntityClass().getSimpleName();
    }

    @SuppressWarnings("unchecked")
	public T get(Serializable id) {
        String name = getEntityName() + GET;
        return (T) super.get(name, id);
    }

    @SuppressWarnings("unchecked")
	public T findUnique(ModelSetup modelSetup) {
        IBatisModelSetup model = (IBatisModelSetup) modelSetup;
        String sqlName = model.getSqlName();
        Assert.hasText(sqlName, "findUnique: sql name not null");        
    	return (T) super.findUnique(sqlName, model.getParameters());
    }
    
    public List<T> getAll() {
        String name = getEntityName() + GETALL;
        return this.getAll(name);
    }

    public List<T> getAll(ModelSetup modelSetup) {
        IBatisModelSetup model = (IBatisModelSetup) modelSetup;
        String sqlName = model.getSqlName();
        Assert.hasText(sqlName, "getAll: sql  name not null");        
        return this.find(sqlName, model.getParameters());
    }
    
    public void update(Object o) {
        String name = getEntityName() + UPDATE;
        this.update(name, o);
    }

    public void create(Object o) {
        String name = getEntityName() + INSERT;
        this.insert(name, o);
    }

    public void remove(Object o) {
        String name = getEntityName() + DELETE;
        this.remove(name, o);
    }

    public void removeById(Serializable id) {
        String name = getEntityName() + DELETEBYID;
        this.removeById(name, id);
    }

    /**
     * 请子类重写返回domain的ID的name
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
	public String getIdName(Class clazz) {
        return "id";
    }

    /**
     * 通过ModelSetup查询count
     *
     * @param modelSetup
     * @return
     */
    public Integer getCount(ModelSetup modelSetup) {
        IBatisModelSetup model = (IBatisModelSetup) modelSetup;
        String countSqlName = model.getCountName();
        Assert.hasText(countSqlName, "getCount: count sql name not null");        
        return super.getCount(countSqlName, model.getParameters());
    }

    /**
     * 通过ModelSetup查询是否唯一
     *
     * @param modelSetup
     * @return
     */
	public Boolean isUnique(ModelSetup modelSetup) {
        IBatisModelSetup model = (IBatisModelSetup) modelSetup;
        String countSqlName = model.getCountName();
        Assert.hasText(countSqlName, "isUnique: count sql name not null");
        return super.isUnique(countSqlName, model.getParameters());
	}
	
    /**
     * 通过ModelSetup  page
     *
     * @param modelSetup
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<T> pagedQuery(ModelSetup modelSetup, int pageNo, int pageSize) {
        IBatisModelSetup model = (IBatisModelSetup) modelSetup;
        String countSqlName = model.getCountName();
        Assert.hasText(countSqlName, "pagedQuery:count sql name not null");
        String sqlName = model.getSqlName();
        Assert.hasText(sqlName, "pagedQuery:sql name not null");
        return super.pagedQuery(countSqlName, sqlName, ((IBatisModelSetup) modelSetup).getParameters(), pageNo, pageSize);
    }
    
}
