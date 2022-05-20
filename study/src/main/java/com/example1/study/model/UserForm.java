package com.example1.study.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserForm {

  private String testString;

  private Integer num;

  private Integer sumNum;

  @NotEmpty
  private String name;

  // @Size(min = 4, max = 20)
  // private String password;

}
