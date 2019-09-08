package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
