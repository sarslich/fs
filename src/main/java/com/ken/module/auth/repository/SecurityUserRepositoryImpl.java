package com.ken.module.auth.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.security.auth.repository.SecurityUserRepository;
import com.ken.core.security.entity.SecurityUser;
import com.ken.module.users.entity.Users;


@Repository
public class SecurityUserRepositoryImpl extends IBatisDao<Users> implements SecurityUserRepository{
	@Value("${security.userSqlName}")
	private String sqlName;
	
	@Override
	public SecurityUser loadSecurityUserByName(String userName) {
		IBatisModelSetup userModelSetup = new IBatisModelSetup();
		userModelSetup.addParameter("userName", userName);
		userModelSetup.setSqlName(sqlName);
		SecurityUser user = findUnique(userModelSetup);
		return user;
	}

}
