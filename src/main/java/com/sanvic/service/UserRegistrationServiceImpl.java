package com.sanvic.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private  UserRepository userRepo;
	private CountryRepository countryRepo;
	private StateRepository stateRepo;
	private CityRepository cityRepo;
	
	@Autowired
	public UserRegistrationServiceImpl
			(UserRepository userRepo, CountryRepository countryRepo, StateRepository stateRepo, CityRepository cityRepo) {
		this.userRepo = userRepo;
		this.countryRepo = countryRepo;
		this.stateRepo = stateRepo;
		this.cityRepo = cityRepo;
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
		
		User userObj = userRepo.save(user);
		return userObj!=null;
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
		// Email Sending Logic
		return userObj!=null;
		
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

}
