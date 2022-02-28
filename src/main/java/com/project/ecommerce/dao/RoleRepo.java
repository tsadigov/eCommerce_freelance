package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findRoleByRoleName(String roleName);
}
