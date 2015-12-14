package com.ken.core.repository.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.Assert;

import com.ken.core.aggregate.Page;
 



/**   
 * @Author： junzhou52@hotmail.com   
 
 * @Date： 2015-5-24 下午12:40:41 
 
 * @Description: IBatis实现数据库操作（暂缺批量操作）；
*/ 
@SuppressWarnings("unchecked")
abstract public class IBatisGenericDao extends SqlSessionDaoSupport {

    /**
     * 根据ID获取对象 DOMAIN
     * @param statement
     * @param id
     * @param <T>
     * @return
     */
    public <T> T get(String statement, Serializable id) {
       return (T) getSqlSession().selectOne(statement, id);
    }


    /**
     * 获取全部对象
     * @param statement 配置的名称
     * @param <T> 方法级泛型
     * @return list
     */
    public <T> List<T> getAll(String statement) {
        return getSqlSession().selectList(statement, null);
    }

    /**
     * 新增对象
     * 先插入數據庫
     */
    public Object insert(String statement, Object o) {
        Object obj = getSqlSession().insert(statement, o);
        return obj;
    }

    /**
     * 保存对象
     */
    public int update(String statement, Object o) {
        int obj = getSqlSession().update(statement, o);
        return obj;
    }

    /**
     * 删除对象
     */
    public int remove(String statement, Object o) {
        int i = getSqlSession().delete(statement, o);
        return i;
    }

    /**
     * 根据ID删除对象
     */
    public int removeById(String statement, Serializable id) {
        int i = getSqlSession().delete(statement, id);
        return i;
    }

	/**
	 * 判断是否存在属性重复的实体对象。
	 * 
	 * @param statement
	 *            sqlmap文件中sql唯一标识
	 * @param params
	 *            属性名，可以多个属性名用","分割
	 * @return 如果存在重复的实体对象返回false，否则返回true。
	 */
	public Boolean isUnique(String statement, Map params) {
		Integer count  = this.getSqlSession().selectOne(statement, params);
		return count == 0;
	}
    
    /**
     * map查询.
     *
     * @param map 包含各种属性的查询
     */
    public <T> List<T> find(String statement, Map map) {
    	return this.getSqlSession().selectList(statement, map);
    }

    /**
     * 根据属性名和属性值查询对象.
     *
     * @return 符合条件的唯一对象
     */
    public <T> T findUnique(String statement, Map map) {

        return (T) getSqlSession().selectOne(statement, map);

    }

    public <T> List<T> list(String statement, Object parameter, int size) {
    	RowBounds rowBounds = new RowBounds(0,size);
        return (List<T>) getSqlSession().selectList(statement, parameter, rowBounds);
    }

    /**
     * 分页查询函数，使用queryForList（），select的Id使用用户自定义
     *
     * @param count     查询count的语句ID
     * @param statement 查询语句ID
     * @param parameter 参数
     * @param pageSize  每页行数
     * @param pageNo    页号,从1开始.
     * @return 含总记录数和当前页数据的Page对象.
     */
    public <T>Page<T> pagedQuery(String count, String statement, Object parameter,
                           int pageNo, int pageSize) {
        //Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        if (pageNo < 1)
            pageNo = 1;

        // 计算总数
        Integer totalCount = (Integer) this.getSqlSession().selectOne(count, parameter);

        // 如果没有数据则返回Empty Page
        Assert.notNull(totalCount, "totalCount Error");

        if (totalCount == null || totalCount.intValue() == 0) {
            return new Page<T>(Page.DEFAULT_PAGE_SIZE);
        }

        List<T> list;
        int totalPageCount = 0;
        int startIndex = 0;

        // 如果pageSize小于等于0,pageSize=Page.DEFAULT_PAGE_SIZE
        if (pageSize <= 0)
            pageSize = Page.DEFAULT_PAGE_SIZE;

        // 计算页数
        totalPageCount = (totalCount / pageSize);
        totalPageCount += (((totalCount % pageSize) > 0) ? 1 : 0);

        // 计算skip数量
        if (totalPageCount > pageNo) {
            startIndex = (pageNo - 1) * pageSize;
        } else {
            startIndex = (totalPageCount - 1) * pageSize;
        }

        RowBounds rowBounds = new RowBounds(startIndex,pageSize);
        list = getSqlSession().selectList(statement, parameter, rowBounds);

        Page<T> page = new Page<T>(totalCount, pageNo, pageSize);
        page.setContents(list);
        return page;
    }


    /**
     * 获得某些条件下的行数
     *
     * @param statement
     * @param params
     * @return
     */
	public int getCount(String statement,Map params){
		return (Integer) this.getSqlSession().selectOne(statement, params);
	}
		
	public String getIdName() {
		return "id";
	}
}
