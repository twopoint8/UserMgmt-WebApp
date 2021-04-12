package com.sanvic.service;

import java.util.List;
import java.util.Map;

import com.sanvic.entity.UnlockAccount;
import com.sanvic.entity.User;

public interface UserRegistrationService {

	public String loginCheck(String emailId, String password);
	
	/*
	 * @Param User object which contains user details
	 */
	public Boolean saveUserDetails(User user);
	
	//public String randomPasswordGenerator();
	
	/*
	 *  @Param user email id to which need to send the email 
	 *  This function is used for two operations
	 *  	1. If user is new and creates new account (if email id does not exists)
	 *  	2. If user already exists and try to use forgot password functionality. (If email id is exists)
	 */
	//public Boolean emailSender(String emailId); 
	
	public User getUserByEmail(String emailId);
	
	/*
	 * @Param New password that will update the temporary password
	 */
	public String unlockAccount(UnlockAccount unlockAccount);
	
	public Boolean forgotPassword(String emailId );
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCities(Integer stateId);
	
	
	
}
