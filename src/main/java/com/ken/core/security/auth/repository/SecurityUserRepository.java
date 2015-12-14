package com.ken.core.security.auth.repository;

import com.ken.core.security.entity.SecurityUser;

public interface SecurityUserRepository {
	
	public SecurityUser loadSecurityUserByName(String userName);
	
}
