package com.ken.module.custinterfaces.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.custinterfaces.entity.CustInterfaces;

@Repository
public class CustInterfacesRepository extends IBatisDao<CustInterfaces> {

}
