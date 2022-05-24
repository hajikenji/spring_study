package com.example1.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example1.study.model.UserRegister;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final JdbcTemplate jdbcTemplate;

  // // @Override
  // // public UserDetails loadUserByUsername(String username)
  // // throws UsernameNotFoundException {

  // // var user = userRepository.findByUsername(username);
  // // if (user == null) {
  // // throw new UsernameNotFoundException(username + " not found");
  // // }
  // // return createUserDetails(user);
  // // }

  // // public User createUserDetails(SiteUser user) {
  // // var grantedAuthorities = new HashSet<GrantedAuthority>();
  // // grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" +
  // user.getRole()));

  // // return new User(user.getUsername(), user.getPassword(),
  // grantedAuthorities);
  // // }

  @Override
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException {

    // userを探してきて存在するかどうかの確認
    String query = "SELECT * FROM spring_study_register WHERE spring_study_register.name = ?";
    List<Map<String, Object>> resultSql = jdbcTemplate.queryForList(query, userName);

    UserRegister user = new UserRegister();
    try {
      user.setName(resultSql.get(0).get("name").toString());
      user.setPassword(resultSql.get(0).get("password").toString());
    } catch (Exception e) {
      throw new UsernameNotFoundException("not fouond");
    }

    if (resultSql.isEmpty()) {
      throw new UsernameNotFoundException(" not found");
    } else {
      return createUserDetails(user);
    }
  }

  public User createUserDetails(UserRegister user) {
    List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
    GrantedAuthority authority = new SimpleGrantedAuthority("USER");
    grantList.add(authority);

    // UserDetails userDetails = (UserDetails) new User(user.getName(),
    // user.getPassword(), grantList);

    return new User(user.getName(), user.getPassword(), grantList);
    // return new User("a", "b", false);
  }

}
