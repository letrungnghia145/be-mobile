package com.nghiale.api.boundary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nghiale.api.config.MailConfig;
import com.nghiale.api.config.MailConfig.MailAction;
import com.nghiale.api.control.UserControl;
import com.nghiale.api.model.CartItem;
import com.nghiale.api.model.Evaluate;
import com.nghiale.api.model.Order;
import com.nghiale.api.model.User;
import com.nghiale.api.utils.JwtUtils;
import com.nghiale.api.utils.RandomUtils;
import com.nghiale.api.utils.TokenUtils;
import com.nghiale.api.utils.mail.AppMail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/users")
public class UserBoundary {
	@Autowired
	private UserControl control;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService service;
	@Autowired
	private JwtUtils jwtUtils;

	@Getter
	@Setter
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private static class TokenResponseWrapper {
		private String token;
		private User user;

		public TokenResponseWrapper(String token, User user) {
			super();
			this.token = token;
			this.user = user;
		}

		public TokenResponseWrapper(String token) {
			super();
			this.token = token;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class UsernamePasswordRequestWrapper {
		private String email;
		private String password;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	private static class TokenValidationCodeRequestWrapper {
		private String validationCode;
		private String token;
	}

	@PostMapping("/authenticate")
	public TokenResponseWrapper authenticate(@RequestBody UsernamePasswordRequestWrapper request) throws Exception {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (AuthenticationException e) {
			throw new AuthenticationCredentialsNotFoundException("Bad credentials");
		}
		final UserDetails userDetails = service.loadUserByUsername(request.getEmail());
		final String token = jwtUtils.doGenerateToken(userDetails);
		return new TokenResponseWrapper(token, control.getUserByUsername(userDetails.getUsername()));
	}

	@PostMapping("/registry")
	public TokenResponseWrapper register(@RequestBody User user) throws JsonProcessingException {
		Map<String, Object> information = new HashMap<>();

		String validationCode = RandomUtils.generateValidationCode();
		information.put("user", user);
		information.put("validationCode", validationCode);
		String token = TokenUtils.generateToken(information);
		System.out.println(user.getRoles());
		MailAction action = MailAction.REGISTER_NEW_ACCOUNT;
		AppMail.send(user.getEmail(), action.name(), MailConfig.createContent(action, validationCode));
		System.out.println(validationCode);
		return new TokenResponseWrapper(token);
	}

	@PostMapping("/registry/confirm")
	public void confirmRegister(@RequestBody TokenValidationCodeRequestWrapper request) throws Exception {
		boolean registryCutomerAccount = control.registryCutomerAccount(request.getToken(),
				request.getValidationCode());
		System.out.println(registryCutomerAccount);
	}

	@GetMapping
	@PreAuthorize("hasAuthority('users:retrieve')")
	public List<User> getAllUsers() {
		return control.getAllUsers();
	}

	@GetMapping("/{uid}")
	@PreAuthorize("hasAuthority('user:retrieve')")
	public User getUser(@PathVariable("uid") Long userID) {
		return control.getUser(userID);
	}

	@GetMapping("/{cid}/evaluates")
	@PreAuthorize("hasAuthority('customer-evaluates:retrieve')")
	public List<Evaluate> getAllEvaluates(@PathVariable("cid") Long customerID) {
		return control.getAllEvaluates(customerID);
	}

	@GetMapping("/{cid}/cart-items")
	@PreAuthorize("hasAuthority('cart:retrieve')")
	public List<CartItem> getCart(@PathVariable("cid") Long customerID) {
		return control.getCart(customerID);
	}

	@PostMapping("/{cid}/cart-items")
	@PreAuthorize("hasAuthority('cart-item:create')")
	public void addItemToCart(@PathVariable("cid") Long customerID, @RequestBody CartItem item) {
		control.addItemToCart(customerID, item);
	}

	@DeleteMapping("/{cid}/cart-items/{iid}")
	@PreAuthorize("hasAuthority('cart-item:delete')")
	public void deleteItemInCart(@PathVariable("cid") Long customerID, @PathVariable("iid") Long itemID) {
		control.deleteItemIncart(customerID, itemID);
	}

	@PutMapping("/{cid}/cart-items/{iid}")
	@PreAuthorize("hasAuthority('cart-item:update')")
	public void updateCart(@PathVariable("cid") Long customerID, @PathVariable("iid") Long itemID,
			@RequestParam String action) {
		control.updateCartItem(customerID, itemID, action);
	}

	@GetMapping("/{cid}/orders")
	@PreAuthorize("hasAuthority('customer-orders:retrieve')")
	public List<Order> getAllOrders(@PathVariable("cid") Long customerID) {
		return control.getAllOrders(customerID);
	}

	@GetMapping("/{cid}/orders/{oid}")
	@PreAuthorize("hasAuthority('customer-order:retrieve')")
	public Order getOrderDetails(@PathVariable("cid") Long customerID, @PathVariable("oid") Long orderID) {
		return control.getOrder(customerID, orderID);
	}
}
