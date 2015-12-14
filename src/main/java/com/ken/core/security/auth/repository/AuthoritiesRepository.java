package com.ken.core.security.auth.repository;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public interface AuthoritiesRepository {
	public List<GrantedAuthority> loadAuthorityByUserName(String userName);
}
