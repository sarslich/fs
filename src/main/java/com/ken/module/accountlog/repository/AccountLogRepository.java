package com.ken.module.accountlog.repository;

import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.module.accountlog.entity.AccountLog;

@Repository
public class AccountLogRepository extends IBatisDao<AccountLog> {

}
