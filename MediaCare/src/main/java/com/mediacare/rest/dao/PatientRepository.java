package com.mediacare.rest.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mediacare.entity.Patient;
import com.mediacare.enums.Gender;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    Optional<Patient> findByEmail(String Email);
    
    @Query(value = "select p from Patient p where email = ?1")
    UserPorition getUserInfo(String email); 	
    
    public interface UserPorition{
    	String getFirstName();
    	String getLastName();
    	String getEmail();
    	Gender getGender();
    }
}


