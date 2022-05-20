package com.example1.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import com.example1.study.controller.SecurityController.InputThing;

@Service
public class SecurityDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  SecurityDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void add(InputThing input) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(input);
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
        // .usingColumns("name")
        .withTableName("spring_study_register");

    insert.execute(param);
  }
}
