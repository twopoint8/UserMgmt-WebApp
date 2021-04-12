package com.sanvic.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sanvic.entity.User;

public interface UserRepository extends JpaRepository<User,Serializable >  {

	public User findByEmail(String emailId);
	
	public User findByEmailAndPassword(String emailId, String password);
	
	@Query("update User set password=:newPassword where userId=:uId")
	public Integer updateTempPawssword(@Param("newPassword") String newPassword, @Param("uId") Integer userId);
}
