package com.nghiale.api;

import com.nghiale.api.model.Role;
import com.nghiale.api.permission.UserRole;

public class Test2 {
	public static void main(String[] args) {
		Role role1 = new Role(UserRole.ROLE_CUSTOMER);
		Role role2 = new Role(UserRole.ROLE_CUSTOMER);
		
		System.out.println(role1.equals(role2));
	}
}
