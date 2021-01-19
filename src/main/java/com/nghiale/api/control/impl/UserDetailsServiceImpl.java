package com.nghiale.api.control.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nghiale.api.entity.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserEntity entity;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsImpl userDetails = new UserDetailsImpl();
		entity.findByEmail(username).ifPresent(user -> {
			Set<GrantedAuthority> authorities = new HashSet<>();
			user.getRoles().forEach(role -> {
				role.getRoleName().getPermission().forEach(permission -> {
					authorities.add(new SimpleGrantedAuthority(permission.toString()));
				});
			});
			userDetails.setAuthorities(authorities);
			userDetails.setPassword(user.getPassword());
			userDetails.setUsername(username);
		});
		return userDetails;
	}

}
