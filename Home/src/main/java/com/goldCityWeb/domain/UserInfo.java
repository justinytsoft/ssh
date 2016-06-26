/**
 * 
 */
package com.goldCityWeb.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Administrator
 *
 */
public class UserInfo extends SysUsers implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Collection<GrantedAuthority> authorities;
	
	public UserInfo() {
		super();
	}

	public UserInfo(String username, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.setUsername(username);
		this.setPassword(password);
		this.authorities = authorities;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
