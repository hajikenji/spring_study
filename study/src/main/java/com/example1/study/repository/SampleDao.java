package com.example1.study.repository;

import java.util.*;

// 他ファイルのレコードの利用はimportが必要らしい
import com.example1.study.controller.SampleController.OutputThing;
import com.example1.study.model.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
public class SampleDao {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  SampleDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void add(UserForm user) {
    // var inputMap = new HashMap<String, String>();
    // record Input(String name) {
    // }
    // ;
    // inputMap.put("name", inputThing);
    // var input1 = new Input(inputThing);
    // BeanPropertySqlParameterSourceは引数がレコードじゃないと内容物がNullになる可能性がある
    SqlParameterSource param = new BeanPropertySqlParameterSource(user);
    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
        // .usingColumns("name")
        .withTableName("spring_study_crud");

    // SqlParameterSource a = new BeanPropertySqlParameterSource("a");
    // System.out.println(a.getValue(bytes));
    // System.out.println(Arrays.toString(a.getParameterNames()));
    // SqlParameterSource a = new BeanPropertySqlParameterSource("a");
    // System.out.println(inputThing);

    insert.execute(param);
  }

  public List<OutputThing> findAll() {
    String query = "SELECT * FROM spring_study_crud";
    List<Map<String, Object>> resultSql = jdbcTemplate.queryForList(query);
    // var result = new ArrayList<String>();
    // resultSql.stream()
    // .forEach(s -> result.add(s.toString()));
    List<OutputThing> result = resultSql.stream()
        .map((Map<String, Object> row) -> new OutputThing(row.get("id").toString(),
            row.get("name").toString()))
        .toList();
    // System.out.println(result.get(0).name());
    // resultSqlの形式は[{id=11, name=e}, {id=12, name=ee}, {id=14, name=ee}]
    // System.out.println(resultSql);
    // System.out.println(result);
    return result;
  }

  public int delete(String id) {
    int number = jdbcTemplate.update("DELETE FROM spring_study_crud WHERE id = ?", id);
    return number;
  }

  public int update(OutputThing info) {
    int number = jdbcTemplate.update("UPDATE spring_study_crud SET name = ? WHERE id = ?",
        info.name(),
        info.id());
    return number;
  }
}
