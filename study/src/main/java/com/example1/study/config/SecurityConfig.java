package com.example1.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // private final UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    // パスワードの暗号化用に、BCrypt（ビー・クリプト）を使用します
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 「/login」と「/error」をアクセス可能にします
        .antMatchers("/login", "/error", "/", "/register").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        // 認証後にリダイレクトする場所を指定
        .defaultSuccessUrl("/success")
        .and()
        // ログアウトの設定
        .logout()
        // ログアウト時のURLを指定
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .and()
        // Remember-Meの認証を許可します
        // これを設定すると、ブラウザを閉じて、
        // 再度開いた場合でも「ログインしたまま」にできます
        .rememberMe();
    // http.csrf().ignoringAntMatchers("/sample1", "/add");
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    auth
        // メモリ内認証を設定
        .inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder().encode("password"))
        .authorities("ROLE_ADMIN")
        .and()
        .withUser("user")
        .password(passwordEncoder().encode("password"))
        .authorities("ROLE_USER");
    // .userDetailsService(userDetailsService)
    // .passwordEncoder(passwordEncoder());
  }
}
