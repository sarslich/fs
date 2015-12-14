package com.ken.module.interfaces.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.interfaces.entity.Interfaces;

@Repository
public class InterfacesRepository extends IBatisDao<Interfaces> {

}
