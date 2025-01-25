package com.cosmo.my_auth_service.repositories;

import com.cosmo.my_auth_service.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoverRepository extends JpaRepository<PasswordRecover, Long> {
}
