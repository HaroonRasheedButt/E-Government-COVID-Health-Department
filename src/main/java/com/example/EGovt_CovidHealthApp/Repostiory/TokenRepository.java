package com.example.EGovt_CovidHealthApp.Repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EGovt_CovidHealthApp.Model.Entity.Token;


/**
 * @author Haroon Rasheed
 * @version 1.1
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByUserIdAndSmsTokenAndEmailToken(long userId, String smsToken, String emailToken);
}
