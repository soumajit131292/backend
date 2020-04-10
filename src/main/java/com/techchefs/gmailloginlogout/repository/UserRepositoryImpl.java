package com.techchefs.gmailloginlogout.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techchefs.gmailloginlogout.model.UserDetails;


@Repository
public interface UserRepositoryImpl extends JpaRepository<UserDetails, Long>{

	@Query(value="select * from user_data  where email = ?1" , nativeQuery = true)
    public Optional<UserDetails> findOneData(String email);
	
	
}
