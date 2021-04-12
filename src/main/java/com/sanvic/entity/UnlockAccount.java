package com.sanvic.entity;

import lombok.Data;

@Data
public class UnlockAccount {

	
	private String email;
	private String tempPassword;
	private String newPassword;
	private String confirmPassword;
	public UnlockAccount(String email, String tempPassword, String newPassword, String confirmPassword) {
		super();
		this.email = email;
		this.tempPassword = tempPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}
	
	
}
