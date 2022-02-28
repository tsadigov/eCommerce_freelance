package com.project.ecommerce.service;

import com.project.ecommerce.dao.RoleRepo;
import com.project.ecommerce.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new ROLE {} to the DB", role.getRoleName());
        return roleRepo.save(role);
    }

}
