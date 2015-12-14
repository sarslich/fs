package com.ken.module.customer.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.customer.CustomerService;
import com.ken.module.customer.entity.Customer;

@Repository
public class CustomerRepository extends IBatisDao<Customer>{
	
}
