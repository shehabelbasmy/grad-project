package com.mediacare.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediacare.entity.MyUser;
import com.mediacare.enums.Authority;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {

	public Optional<MyUser> findByEmail(String email);

}
