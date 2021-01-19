package com.nghiale.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nghiale.api.permission.UserRole;

import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends AbstractModel {
	private static final long serialVersionUID = 3340536082287241801L;
	@Include
	@Column(unique = true)
	private UserRole roleName;
	private String description;

	@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<User> users = new HashSet<>();

	public Role(Long id) {
		super(id);
	}

	@JsonCreator
	public Role(UserRole roleName) {
		super();
		this.roleName = roleName;
		this.description = roleName.getDescription();
	}
}
