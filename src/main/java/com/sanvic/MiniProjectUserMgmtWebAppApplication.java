package com.sanvic;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sanvic.entity.User;
import com.sanvic.service.UserRegistrationServiceImpl;

@SpringBootApplication
public class MiniProjectUserMgmtWebAppApplication {

	public static void main(String[] args) {
		 SpringApplication.run(MiniProjectUserMgmtWebAppApplication.class, args);
		//UserRegistrationServiceImpl bean = run.getBean(UserRegistrationServiceImpl.class);
		
		
	}

}
