package com.ken.module.auth.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.ken.core.repository.ibatis.IBatisDao;
import com.ken.core.repository.support.ibatis.IBatisModelSetup;
import com.ken.core.security.auth.repository.AuthoritiesRepository;
import com.ken.core.security.entity.Authorities;


@Repository
public class AuthoritiesRepositoryImpl extends IBatisDao<Authorities> implements AuthoritiesRepository{
	
	@Value("${security.authoritesSqlName}")
	private String sqlName;
	
	@Override
	public List<GrantedAuthority> loadAuthorityByUserName(String userName) {
		IBatisModelSetup  authoritiesModelSetup = new IBatisModelSetup();
		authoritiesModelSetup.addParameter("userName", userName);
		authoritiesModelSetup.setSqlName(sqlName);
		
		List<Authorities> list = getAll(authoritiesModelSetup);

		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		for (Authorities authority : list) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
					authority.getAuthority());
			auths.add(grantedAuthority);
		}
		return auths;
	}

}
