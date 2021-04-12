package com.sanvic.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanvic.entity.State;

public interface StateRepository extends JpaRepository<State, Serializable> {

	public List<State> findByCountryId(Integer counryId);
}
