package com.billsTech.PortalSystem01.repositories;

import com.billsTech.PortalSystem01.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo2 extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

}
