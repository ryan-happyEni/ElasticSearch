package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository  extends JpaRepository<User, Long> {

	@Query(value = "select * from user m where m.username=:username", nativeQuery = true)
	User readUser(@Param("username") String username );

	@Query(value = "select * from user m where m.username=:username", nativeQuery = true)
	User findByUsername(@Param("username") String username);

}
