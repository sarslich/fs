package com.ken.module.order.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.order.entity.Order;

@Repository
public class OrderRepository extends IBatisDao<Order> {

}
