package com.bikram.appliedproject.repositories;

import com.bikram.appliedproject.domain.authentication.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

}
