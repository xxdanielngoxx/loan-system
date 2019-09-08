package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Boolean existsByEmailIgnoreCase(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Boolean existsByIdentityCardIgnoreCase(String identityCard);
}
