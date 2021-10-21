package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.authentication.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String s);

    User findByEmail(String s);

    Optional<User> findOneWithAuthoritiesByUsername(String login);

}
