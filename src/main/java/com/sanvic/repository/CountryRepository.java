package com.sanvic.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanvic.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Serializable> {

}
