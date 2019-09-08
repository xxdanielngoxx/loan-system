package com.locngo.loansystem.service;

import com.locngo.loansystem.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getRoleByName(String name);
}
