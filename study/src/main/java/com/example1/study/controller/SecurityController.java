package com.example1.study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

import com.example1.study.model.UserRegister;
import com.example1.study.controller.SecurityDao;

@RequiredArgsConstructor
@Controller
public class SecurityController {

  record InputThing(String name, String password) {
  }

  private final SecurityDao dao;
  private final PasswordEncoder passwordEncoder;

  /// @RequiredArgsConstructor が代用してくれている
  // @Autowired
  // SecurityController(SecurityDao dao) {
  // this.dao = dao;
  // }

  // ログインまたは新規登録画面へのリンクを出すページ
  @GetMapping("/")
  public String root() {

    dao.find();

    return "root";
  }

  // 新規登録画面の処理
  @GetMapping("/register")
  public String registerVisit(@Validated UserRegister user,
      BindingResult result) {
    if (result.hasErrors()) {
      System.out.println("2");
      return "register";
    }
    var password = passwordEncoder.encode(user.getPassword());
    InputThing input = new InputThing(user.getName(), password);
    // InputThing input = new InputThing(user.getName(), user.getPassword());
    System.out.println(password);
    System.out.println(user.getPassword().getClass().getSimpleName());
    // UserRegister aa = "a";
    dao.add(input);
    return "register";
  }

  // // 新規登録処理
  // @RequestMapping("/entry")
  // public String registerUser(@Validated UserForm user,
  // BindingResult result) {
  // if (result.hasErrors()) {
  // // return "register";
  // return "redirect:/register";
  // }
  // // return "redirect:/register";
  // return "register";
  // }

  @GetMapping("/success")
  public String success(Authentication loginUser, Model model) {
    model.addAttribute("username", loginUser.getName());
    model.addAttribute("role", loginUser.getAuthorities());
    return "success";
  }

}
