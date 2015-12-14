package com.ken.module.product.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.product.entity.Product;

@Repository
public class ProductRepository extends IBatisDao<Product> {

}
