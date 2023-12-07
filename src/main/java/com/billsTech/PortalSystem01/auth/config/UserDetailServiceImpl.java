package com.billsTech.PortalSystem01.auth.config;

import com.billsTech.PortalSystem01.auth.Roles;
import com.billsTech.PortalSystem01.entity.Users;
import com.billsTech.PortalSystem01.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),true, true, true, true, getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Users user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        authorities.add(new SimpleGrantedAuthority(Roles.LECTURER.name()));
        authorities.add(new SimpleGrantedAuthority(Roles.ADMIN.name()));
        authorities.add(new SimpleGrantedAuthority(Roles.PARENT.name()));
        return authorities;
    }
}
