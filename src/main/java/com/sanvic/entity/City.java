package com.sanvic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CITY_MASTER")
public class City {
		@Id
		@Column(name = "CITY_ID")
		private Integer cityId;
		
		@Column(name = "CITY_NAME")
		private String cityName;
		
		@Column(name = "STATE_ID")
		private Integer stateId;
}

