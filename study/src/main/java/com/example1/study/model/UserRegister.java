package com.example1.study.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRegister {
  @NotEmpty
  private String name;

  @Size(min = 4, max = 20)
  private String password;
}
