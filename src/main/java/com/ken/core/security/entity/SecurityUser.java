package com.ken.core.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ken.core.entity.Entity;



/**   
 * @Author�� junzhou52@hotmail.com   
 
 * @Date�� 2015-6-12 ����5:31:21 
 
 * @Description: User����
*/ 
public class SecurityUser extends Entity<String> implements UserDetails  {

	private static final long serialVersionUID = -1589402718779286296L;
	private String userName;  
	private String password;
	
	private boolean  enabled;
	private Long roleId;
	private Long[] roleIds;
	private Collection<? extends GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {		 
		return this.authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {		 
		this.authorities = authorities;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	@Override
	public String getUsername() {		
		return this.userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {		
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
