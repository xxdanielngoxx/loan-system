package com.locngo.loansystem.repository;

import com.locngo.loansystem.model.Investment;
import com.locngo.loansystem.model.Lender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
}
