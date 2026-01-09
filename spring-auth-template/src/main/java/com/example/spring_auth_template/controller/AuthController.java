package com.example.spring_auth_template.controller;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.spring_auth_template.form.SignupForm;
import com.example.spring_auth_template.security.UserDetailsServiceImpl;
import com.example.spring_auth_template.service.UserService;
@Controller
public class AuthController {
  private final UserService userService;
  private final UserDetailsServiceImpl userDetailsServiceImpl;
  
  public AuthController(UserService userService, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.userService = userService;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @GetMapping("/login")
  public String login() {
    return "login/login";
  }

  @GetMapping("/signup")
  public String signup(Model model) {
    model.addAttribute("signupForm", new SignupForm());
    return "auth/signup";
  }

  @PostMapping("/signup")
  public String signup(@ModelAttribute @Validated SignupForm signupForm,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes,
      Model model,
      HttpServletRequest request) {
    // メールアドレスが登録済みであれば、BindingResultオブジェクトにエラー内容を追加する
    if (userService.isEmailRegistered(signupForm.getEmail())) {
      FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "すでに登録済みのメールアドレスです。");
      bindingResult.addError(fieldError);
    }

    // パスワードとパスワード（確認用）の入力値が一致しなければ、BindingResultオブジェクトにエラー内容を追加する
    if (!userService.isSamePassword(signupForm.getPassword(), signupForm.getPasswordConfirmation())) {
      FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
      bindingResult.addError(fieldError);
    }

    if (bindingResult.hasErrors()) {
      // 全てのエラーメッセージをリストに詰める
      List<String> errList = bindingResult.getAllErrors()
              .stream()
              .map(error -> error.getDefaultMessage())
              .toList();

      model.addAttribute("errList", errList);
      model.addAttribute("signupForm", signupForm);

      return "auth/signup";
    }
    // 会員登録
    userService.createUser(signupForm);
    // 自動ログイン部分をこれだけにする
    try {
        request.login(signupForm.getEmail(), signupForm.getPassword());
    } catch (ServletException e) {
        // 適切なエラーページへ遷移してください
    }
     
    redirectAttributes.addFlashAttribute("successMessage", "会員登録が完了しました。");

    return "redirect:/";
  }
}
