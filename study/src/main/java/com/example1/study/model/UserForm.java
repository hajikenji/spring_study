package com.example1.study.model;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserForm {

  private String testString;

  private Integer num;

  @NotEmpty
  private String name;

  private Integer sumNum;

}
