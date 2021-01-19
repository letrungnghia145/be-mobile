package com.nghiale.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan(basePackages = "com.nghiale.api.model")
public class ApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Role role = new Role(UserRole.ROLE_CUSTOMER);
//		manager.persist(role);
//		User user = new User("Le Trung Nghia", "0855850309", "Tay Ninh", "17130132@st.hcmuaf.edu.vn", "admin123");
//		user.addRole(manager.find(Role.class, 1L));
//		manager.persist(user);
	}
}
