// package com.example1.study.service;

// import java.util.HashSet;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

// @Override
// public UserDetails loadUserByUsername(String username)
// throws UsernameNotFoundException {

// var user = userRepository.findByUsername(username);
// if (user == null) {
// throw new UsernameNotFoundException(username + " not found");
// }
// return createUserDetails(user);
// }

// public User createUserDetails(SiteUser user) {
// var grantedAuthorities = new HashSet<GrantedAuthority>();
// grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

// return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
// }
// }
