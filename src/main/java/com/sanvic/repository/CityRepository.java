package com.sanvic.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanvic.entity.City;

public interface CityRepository extends JpaRepository<City, Serializable> {

	//public List<City> findByStateId(Integer stateId);
}
