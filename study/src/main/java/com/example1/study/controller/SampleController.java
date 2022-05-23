package com.example1.study.controller;

import com.example1.study.model.UserForm;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.*;
import org.springframework.validation.BindingResult;

@Controller
public class SampleController {

  record InputThing(String name) {
  }

  record OutputThing(String id, String name) {
  }

  // private List<InputThing> InputThings = new ArrayList<>();

  private String testString;

  private Integer num = 0;

  private Integer sumNum = 0;
  private final SampleDao dao;

  @Autowired
  SampleController(SampleDao dao) {
    this.dao = dao;
  }

  @RequestMapping("/sample")
  public String sample() {
    return "sample";
  }

  @GetMapping("/sample1")
  String number(Model model, @ModelAttribute UserForm user) {
    var name = dao.findAll();
    var nameList = dao.findAll();

    // newをしないと下記エラーが発生。
    // Cannot make a static reference to the non-static method getNum() from the
    /// type UserForm
    // UserForm user = new UserForm();

    model.addAttribute("numPresence", this.num);
    model.addAttribute("numAdd", this.sumNum);
    model.addAttribute("name", name);
    model.addAttribute("nameList", nameList);
    this.num += 1;
    return "sample1";
  }

  // 数字がtextできたらキャストする
  // なんでここgetじゃなくてpostなん？
  // @PostMapping("/add")
  // String addInfo(@RequestParam("num") String inputNum,
  // @RequestParam("name") String inputName,
  // @RequestParam("testString") String testString) {

  // if (inputNum.isEmpty()) {
  // this.sumNum = -1000 + this.num;
  // } else {
  // Integer num = Integer.parseInt(inputNum);
  // this.sumNum = num + this.num;
  // }
  // // Integer num = Integer.parseInt(inputNum);
  // // this.sumNum = num + this.num;
  // // modelで送った後リダイレクトなどするとmodel消える
  // // model.addAttribute("numAdd", sumNum);

  // InputThing input = new InputThing(inputName);

  // this.testString = testString;
  // // System.out.println(this.testString);

  // dao.add(input);

  // return "redirect:/sample1";
  // }

  @PostMapping("/add")
  String addTest(@Valid @ModelAttribute UserForm userForm,
      BindingResult result,
      Model model) {
    if (userForm.getNum() == null) {
      this.sumNum = -1000 + this.num;
    } else {
      this.sumNum = userForm.getNum() + this.num;
    }

    if (result.hasErrors()) {
      userForm.setSumNum(3);
      number(model, userForm);
      return "/sample1";
    }
    System.out.println(1);

    InputThing input = new InputThing(userForm.getName());
    dao.add(input);

    return "redirect:/sample1";
  }

  // idの形式がStringで{id=1, name=ee}になってしまい教科書通りにいかない問題。元々のデータ型のせい？
  @GetMapping("/delete")
  String deleteInfo(@RequestParam("id") String id) {

    // 文字列で来た時用
    // System.out.println(id);
    // var str = id.replaceAll("[^0-9]", "");
    // System.out.println(str);
    // dao.delete(str);
    // System.out.println(id);

    dao.delete(id);
    return "redirect:/sample1";
  }

  @RequestMapping("/update")
  String updateInfo(@RequestParam("name") String name,
      @RequestParam("id") String id) {
    OutputThing input = new OutputThing(id, name);
    dao.update(input);
    return "redirect:/sample1";
  }

  // htmlテスト用
  // @RequestMapping("/sample1")
  // public String sample1() {
  // return "sample1";
  // }
  // @GetMapping("/sample1")
  // void number() {
  // // return this.num;
  // this.num += 1;
  // }

  // ログイン機能のユーザー登録

}

// @Controller
// public class Sample1Controllor {

// private Integer num1 = 0;

// @GetMapping("/sample1")
// Integer number() {
// return this.num1;
// }

// }