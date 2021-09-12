package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.authentication.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String s);

    User findByEmail(String s);

}
