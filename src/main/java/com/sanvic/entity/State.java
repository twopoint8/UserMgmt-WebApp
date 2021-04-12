package com.sanvic.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "STATES_MASTER")
public class State {

		@Id
		@Column(name = "STATE_ID")
		private Integer stateId;
		
		@Column(name = "STATE_NAME")
		private String stateName;
		
		@Column(name = "COUNTRY_ID")
		private Integer countryId;

}
