package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.authentication.User;
import com.bikram.appliedproject.domain.authentication.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
}
