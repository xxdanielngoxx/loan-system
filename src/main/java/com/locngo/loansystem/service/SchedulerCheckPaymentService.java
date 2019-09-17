package com.locngo.loansystem.service;

import org.springframework.stereotype.Service;

@Service
public interface SchedulerCheckPaymentService {
    void updatePaymentDaily();
}
