package com.recurrent.spring_mybatis.controller.api;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.recurrent.spring_mybatis.form.SignupForm;
import com.recurrent.spring_mybatis.service.UserService;

@RestController
public class AuthApiController {
  private final UserService userService;
  
  public AuthApiController(UserService userService) {
    this.userService = userService;
  }
  @PostMapping("/api/signup")
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
