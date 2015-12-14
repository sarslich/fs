package com.ken.core.security.auth.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ken.core.security.auth.repository.AuthoritiesRepository;
import com.ken.core.security.auth.repository.SecurityUserRepository;
import com.ken.core.security.entity.SecurityUser;


//ʵ��Spring Security UserDetailsService����ͨ��ע��Dao��ModelSetup��ʵ���Զ��壻
public class SecurityUserService implements UserDetailsService {

 

	@Autowired(required = false)
	private UserCache userCache;

	@Resource
	private SecurityUserRepository securityUserRepository; //�����Լ�����

	@Resource
	private AuthoritiesRepository authoritiesRespository; //�����Լ�����

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		SecurityUser user = null;
		if (userCache != null)
			user = (SecurityUser) userCache.getUserFromCache(userName);
		if (user == null) {
			user = securityUserRepository.loadSecurityUserByName(userName);
		}
		if (user == null)
			throw new UsernameNotFoundException("user name/loging name:"
					+ userName + " is not found");

		List<GrantedAuthority> auths = authoritiesRespository
				.loadAuthorityByUserName(userName);
		user.setAuthorities(auths);

		if (userCache != null)
			userCache.putUserInCache(user);
		return user;
	}

}
