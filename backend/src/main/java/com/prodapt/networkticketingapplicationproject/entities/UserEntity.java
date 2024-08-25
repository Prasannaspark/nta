package com.prodapt.networkticketingapplicationproject.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "user_table", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })

public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String username;

	private String email;

	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private CustomerTier tier;  //type enum
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Request request;

    @ManyToOne
	private Role role ;
	
	public UserEntity(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}


}
