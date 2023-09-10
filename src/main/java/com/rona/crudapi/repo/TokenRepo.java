package com.rona.crudapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rona.crudapi.models.Token;

public interface TokenRepo extends JpaRepository<Token, Integer>{
	// get by user id
	@Query(value = "SELECT * FROM token WHERE user_id = ?1", nativeQuery = true)
	Token findByUserId(int user_id);

	// get by email
	@Query(value = ""
			+ "SELECT token.* FROM token "
			+ "LEFT JOIN user ON token.user_id = user.id "
			+ "WHERE user.email = ?1", nativeQuery = true)
	Token findByEmail(String email);
}
