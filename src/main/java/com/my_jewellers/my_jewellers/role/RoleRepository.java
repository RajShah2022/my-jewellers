package com.my_jewellers.my_jewellers.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByRoleName(String roleName);
}
