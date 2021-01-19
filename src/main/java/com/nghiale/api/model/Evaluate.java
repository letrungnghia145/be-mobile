package com.nghiale.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nghiale.api.serialize.EvaluateSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({ "id", "product", "customer" })
@JsonSerialize(using = EvaluateSerialize.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Evaluate extends AbstractModel {
	private static final long serialVersionUID = 1040766522531247812L;
	@Column(unique = true)
	private String evaluateCode;
	private String comment;
	@Column(columnDefinition = "int(5)")
	private Integer value;

//	private String customerPhone;
//
//	private String customerName;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	private User customer;

	public Evaluate(Long id) {
		super(id);
	}

	@JsonCreator
	public Evaluate(String comment, Integer value, Long customerID) {
		super();
		this.comment = comment;
		this.value = value;
		this.customer = new User(customerID);
	}

}
