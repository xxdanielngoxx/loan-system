package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Boolean existsByName(String name);
}
