package com.example1.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
        .withTableName("spring_study_register");

    if (!find(input.name()).isEmpty()) {
      return;
    }

    System.out.println(!find(input.name()).isEmpty());

    // try {
    // find(input.name()).isEmpty();
    // } catch (Exception e) {
    // throw new UncheckedIOException(e);
    // }

    find(input.name());
    System.out.println(input);

    insert.execute(param);
  }

  // 新規登録で名前をDBに登録する前に重複チェック。名前のダブりはNGにする
  public List<Map<String, Object>> find(String name) {
    String query = "SELECT 1 FROM spring_study_register WHERE spring_study_register.name = ? ";
    List<Map<String, Object>> resultSql = jdbcTemplate.queryForList(query, name);
    return resultSql;
  }
}
