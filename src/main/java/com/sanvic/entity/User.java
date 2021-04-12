package com.sanvic.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "USER_ACCOUNTS")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Integer userId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "DOB")
	private Date dob;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "USER_MOBILE")
	private Long phno;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "COUNTRY_ID")
	private Integer country_id;
	
	@Column(name = "STATE_ID")
	private Integer state_id;
	
	@Column(name = "CITY_ID")
	private Integer city_id;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "STATUS")
	private String accountStatus;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
}
