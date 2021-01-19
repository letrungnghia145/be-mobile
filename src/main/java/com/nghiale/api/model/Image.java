package com.nghiale.api.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({ "product" })
public class Image extends AbstractModel {
	private static final long serialVersionUID = -7036987774546848193L;
	private String link;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@JsonCreator
	public Image(String link, String description) {
		super();
		this.link = link;
		this.description = description;
	}

	public Image(Long id) {
		super(id);
	}

	public Image(Long id, String link, String description) {
		super(id);
		this.link = link;
		this.description = description;
	}

}
