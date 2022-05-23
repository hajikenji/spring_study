package com.example1.study.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

import com.example1.study.model.UserRegister;

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

    return "root";
  }

  // 新規登録画面の処理
  @GetMapping("/register")
  public String registerVisit(UserRegister user) {
    return "register";
  }

  @PostMapping("/register/add")
  public String registerAdd(@Validated UserRegister user,
      BindingResult result, Model model) {
    if (result.hasErrors()) {
      System.out.println(result);
      return "register";
    }
    var password = passwordEncoder.encode(user.getPassword());
    InputThing input = new InputThing(user.getName(), password);
    // InputThing input = new InputThing(user.getName(), user.getPassword());
    // System.out.println(password);
    // System.out.println(user.getPassword().getClass().getSimpleName());
    String resultValidation = dao.add(input);
    if (resultValidation.equals("duplicate")) {
      System.out.println(resultValidation);
      model.addAttribute("originalErrorDup", true);
      return "register";
    }
    return "redirect:/register";
  }

  @GetMapping("/success")
  public String success(Authentication loginUser, Model model) {
    model.addAttribute("username", loginUser.getName());
    model.addAttribute("role", loginUser.getAuthorities());
    return "success";
  }

}
