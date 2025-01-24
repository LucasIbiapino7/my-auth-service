package com.cosmo.my_auth_service.repositories;

import com.cosmo.my_auth_service.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
