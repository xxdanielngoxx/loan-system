package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Lender;
import com.locngo.loansystem.model.User;
import com.locngo.loansystem.request.lender.LenderCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LenderService {

    Lender createLender(LenderCreateRequest request);

    List<Lender> getAllLender();

    Lender getLender(Long id);

    Lender getCurrentLender();
}
