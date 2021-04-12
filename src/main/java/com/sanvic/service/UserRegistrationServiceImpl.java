package com.sanvic.service;

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
		
		 User userObj = null;//userRepo.findByEmail(emailId);
		 if(userObj==null) {
			 return "USER NOT FOUND";
		 }
		 else if(userObj.getAccountStatus().equals("LOCKED")) {
			 return "ACCOUNT LOCKED";
		 }
		 else if(password.equals(userObj.getPassword())) {
			 return "VALID USER CREDENTIALS";
		 }
		 else {
			 return "INVALID USER CREDENTIALS";
		 }
		
	}

	@Override
	public Boolean saveUserDetails(User user) {
		
		User userObj = userRepo.save(user);
		return userObj!=null;
	}

	@Override
	public User getUserByEmail(String emailId) {

		User userObj = null;//userRepo.findByEmail(emailId);
		
		return userObj;
	}

	@Override
	public String unlockAccount(UnlockAccount unlockAccount) {
		
		User userObj = null;// userRepo.findByEmail(unlockAccount.getEmail());
		
		if(userObj.getPassword().equals(unlockAccount.getTempPassword())) {
			
			if(unlockAccount.getNewPassword().equals(unlockAccount.getConfirmPassword())) {
				//System.out.println("Update value - > "+ userRepo.updateTempPawssword(unlockAccount.getNewPassword(), userObj.getUserId()));
				return "ACCOUNT UNLOCKED";
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

		return userObj!=null;
		
	}

	@Override
	public Map<Integer, String> getCountries() {

		Map<Integer, String> data = new HashMap<Integer, String>();
		List<Country> countryList = countryRepo.findAll();
		
		countryList.forEach(country->{
			
			data.put(country.getCountryId(), country.getCountryName());
		});
		return data;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		
		 List<State> stateObj = null;//stateRepo.findByCountryId(countryId);
		return null;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {

		 List<City> cityList = null; //cityRepo.findByStateId(stateId);
		return null;
	}

}
