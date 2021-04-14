package com.sanvic.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringBufferInputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanvic.entity.City;
import com.sanvic.entity.Country;
import com.sanvic.entity.State;
import com.sanvic.entity.UnlockAccount;
import com.sanvic.entity.User;
import com.sanvic.repository.CityRepository;
import com.sanvic.repository.CountryRepository;
import com.sanvic.repository.StateRepository;
import com.sanvic.repository.UserRepository;
import com.sanvic.utils.EmailUtils;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private  UserRepository userRepo;
	private CountryRepository countryRepo;
	private StateRepository stateRepo;
	private CityRepository cityRepo;
	private EmailUtils emailUtils;
	
	@Autowired
	public UserRegistrationServiceImpl
			(UserRepository userRepo, CountryRepository countryRepo, StateRepository stateRepo, CityRepository cityRepo, EmailUtils emailUtils) {
		this.userRepo = userRepo;
		this.countryRepo = countryRepo;
		this.stateRepo = stateRepo;
		this.cityRepo = cityRepo;
		this.emailUtils = emailUtils;
	}
	
	@Override
	public String loginCheck(String emailId, String password) {
		
		 User userObj = userRepo.findByEmail(emailId);
		 if(userObj==null) {
			 return "USER NOT FOUND";
		 }
		 else if(!password.equals(userObj.getPassword())) {
			 return "INVALID USER CREDENTIALS";
		 } 
		 else if(userObj.getAccountStatus().equalsIgnoreCase("LOCKED")) {
			 return "ACCOUNT LOCKED";
		 }
		 else {
			 return "VALID USER CREDENTIALS";
		 }
		 
		
	}

	@Override
	public Boolean saveUserDetails(User user) {
		user.setCreatedDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		user.setDob(new SimpleDateFormat("dd-MM-yyyy").format(new Date()) );
		user.setUpdatedDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		String tempPassword = randomPasswordGenerator(8);
		user.setPassword(tempPassword);
		User userObj = userRepo.save(user);
		if(userObj != null) {
			return sendAccRegEmail(userObj);
		}
		else
			return false;
	}

	@Override
	public User getUserByEmail(String emailId) {

		User userObj = userRepo.findByEmail(emailId);
		
		return userObj;
	}

	@Override
	public String unlockAccount(UnlockAccount unlockAccount) {
		
		
		User userObj = userRepo.findByEmail(unlockAccount.getEmail());
		if(userObj == null)
			return "USER NOT FOUND";
		
		if(userObj.getAccountStatus().equalsIgnoreCase("unlock")) {
			return "USER ALREADY UNLOCK";
		}
		if(userObj.getPassword().equals(unlockAccount.getTempPassword())) {
			
			if(unlockAccount.getNewPassword().equals(unlockAccount.getConfirmPassword())) {
				if((userRepo.updateTempPawssword(unlockAccount.getNewPassword(), "UNLOCK", userObj.getUserId()))>0)
				return "ACCOUNT UNLOCKED";
				else 
					return "ERROR IN UNLOCKING ACCOUNT";
			}
			else {
				return "PASSWORD NOT MATCHED";
			}
		}
		else {
			return "WRONG TEMP PASSWORD";
		}
		
	}

	@Override
	public Boolean forgotPassword(String emailId) {
		User userObj = getUserByEmail(emailId);
		Boolean isSent = sendForgotPassEmail(userObj);
		if(isSent != null) {
			return true;
		}
		else {
			return false;
		}		
	}

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> data = new HashMap<Integer, String>();
		List<Country> countryList = countryRepo.findAll();
		//System.out.println(countryList);
		countryList.forEach(country->{
			
			data.put(country.getCountryId(), country.getCountryName());
		});
		return data;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		
		Map<Integer, String> data = new HashMap<Integer, String>();
		 List<State> stateList = stateRepo.findByCountryId(countryId);
		// System.out.println(stateList);
		 stateList.forEach(state->{
				
				data.put(state.getStateId(), state.getStateName());
			});
		return data;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		Map<Integer, String> data = new HashMap<Integer, String>();
		 List<City> cityList = cityRepo.findByStateId(stateId);
		// System.out.println(cityList);
		 cityList.forEach(city->{
				
				data.put(city.getCityId(), city.getCityName());
			});
		return data;
	}

	private String randomPasswordGenerator(int length) {
		
		byte[]  array = new byte[256];
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		StringBuffer r = new StringBuffer();
        for (int k = 0; k < generatedString.length(); k++) {
  
            char ch = generatedString.charAt(k);
  
            if (((ch >= 'a' && ch <= 'z')
                 || (ch >= 'A' && ch <= 'Z')
                 || (ch >= '0' && ch <= '9'))
                && (length > 0)) {
  
                r.append(ch);
                length--;
            }
        }
  
		return r.toString();
	}
	
	private Boolean sendForgotPassEmail(User user) {
		String body = getForgotPassEmailBody(user);
		return emailUtils.sendAccRegMail("Sanvic.Inc: Password for Account", body, user.getEmail());
	}
	
	private String getForgotPassEmailBody(User user) {
		String mailBody = null;
		StringBuilder sb = new StringBuilder();
		try {
		FileReader fr = new FileReader("USER_FORGOT_PASS_EMAIL_BODY_TEMPLATE.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();
		
		while(line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();
		fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		mailBody = sb.toString();
		mailBody = mailBody.replace("{fname}", user.getFirstName());
		mailBody = mailBody.replace("{lname}", user.getLastName());
		mailBody = mailBody.replace("{email}", user.getEmail());
		mailBody = mailBody.replace("{password}", user.getPassword());
	
		return mailBody;
	}
	private Boolean sendAccRegEmail(User user) {
		
		String body = getRegAccEmailBody(user);
		return emailUtils.sendAccRegMail("Sanvic.Inc: Registration Successful", body, user.getEmail());
	}
	
	private String getRegAccEmailBody(User user) {
		String mailBody = null;
		StringBuilder sb = new StringBuilder();
		try {
		FileReader fr = new FileReader("USER_ACC_REG_EMAIL_BODY_TEMPLATE.txt");
		BufferedReader br = new BufferedReader(fr);
		
		String line = br.readLine();
		
		while(line != null) {
			sb.append(line);
			line = br.readLine();
		}
		br.close();
		fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		mailBody = sb.toString();
		mailBody = mailBody.replace("{fname}", user.getFirstName());
		mailBody = mailBody.replace("{lname}", user.getLastName());
		mailBody = mailBody.replace("{temp-pass}", user.getPassword());
		return mailBody;
	}
}
