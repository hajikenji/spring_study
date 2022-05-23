package com.example1.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

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

  public String add(InputThing input) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(input);
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("spring_study_register");

    // 重複チェック
    if (!find(input.name()).isEmpty()) {
      return "duplicate";
    }

    // try {
    // find(input.name()).isEmpty();
    // } catch (Exception e) {
    // throw new UncheckedIOException(e);
    // }

    insert.execute(param);
    return "";
  }

  // 新規登録で名前をDBに登録する前に重複チェック。名前のダブりはNGにする
  public List<Map<String, Object>> find(String name) {
    String query = "SELECT 1 FROM spring_study_register WHERE spring_study_register.name = ? ";
    List<Map<String, Object>> resultSql = jdbcTemplate.queryForList(query, name);
    return resultSql;
  }
}
