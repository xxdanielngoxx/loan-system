package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LenderRepository extends JpaRepository<Lender, Long> {

    Boolean existsByEmailIgnoreCase(String email);

    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByIdentityCardIgnoreCase(String identityCard);

    Optional<Lender> findByUser(User user);
}
