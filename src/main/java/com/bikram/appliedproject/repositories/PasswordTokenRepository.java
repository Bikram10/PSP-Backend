package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.authentication.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}
