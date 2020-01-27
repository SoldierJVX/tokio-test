package com.example.api.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@NotEmpty(message = "is required")
	private String name;

	@Column(nullable = false)
	@NotEmpty(message = "is required")
	@Email(message = "insert a valid email")
	private String email;
}
