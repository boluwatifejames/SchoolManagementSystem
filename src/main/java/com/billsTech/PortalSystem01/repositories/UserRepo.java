package com.billsTech.PortalSystem01.repositories;

import com.billsTech.PortalSystem01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
//    Users findByEmaill(String email);
}
