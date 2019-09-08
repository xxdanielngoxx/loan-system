package com.locngo.loansystem.service.impl;

import com.locngo.loansystem.errorhandling.error.DataNotFoundException;
import com.locngo.loansystem.model.Role;
import com.locngo.loansystem.repository.RoleRepository;
import com.locngo.loansystem.service.RoleService;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByRole(name).orElseThrow(DataNotFoundException::new);
    }
}
