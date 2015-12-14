package com.ken.module.auth.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.security.auth.repository.SysResourceRepository;

 

public class AuthoritiesResourcesRepositoryImpl extends IBatisDao implements
		SysResourceRepository {

	@Value("${security.sysResourceSqlName}")
	private String sqlName;

	@Override
	public List<Map<String, String>> getResourceMapping() {
		IBatisModelSetup sysResourceModelSetup = new IBatisModelSetup();
		sysResourceModelSetup.setSqlName(sqlName);

		List<Map<String, String>> list = getAll(sysResourceModelSetup);
		return list;
	}

}
