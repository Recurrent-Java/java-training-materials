package com.example.java_extra_spring.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.java_extra_spring.form.MemberForm;
import com.example.java_extra_spring.service.MemberService;

@Controller
public class MemberController {
  // MessageSource を注入する
  private final MessageSource messageSource;
  private final MemberService memberService;

  public MemberController(MessageSource messageSource, MemberService memberService) {
    this.messageSource = messageSource;
    this.memberService = memberService;
  }

  // 最初のアクセス
  @GetMapping("/")
  public String form(HttpSession session, Model model) {
    // セッション破棄
    session.invalidate();
    // バリデーション用にMemberFormインスタンスを生成
    MemberForm memberForm = new MemberForm();
    // メール配信希望の初期値を希望するにするため、値をセット
    memberForm.setMailFlg("yes");
    // バリデーション用に空のインスタンスをセット
    model.addAttribute("member", memberForm);
    return "register/form";
  }

  // 確認ボタン押下時の処理
  @PostMapping("/confirm")
  public String member(
      @Valid @ModelAttribute("member") MemberForm form,
      BindingResult result,
      HttpSession session,
      Model model) {
    // バリデーションエラーがある場合
    if (result.hasErrors()) {
      /** フィールド順序に基づいてエラーメッセージをソートする場合は、以下の処理が必要 */
      //--------------------------------------------------- ▼ Start ▼
      List<String> fieldOrder = List.of("name", "age", "gender", "role");

      List<String> errList = result.getFieldErrors().stream()
          .sorted((e1, e2) -> {
            int i1 = fieldOrder.indexOf(e1.getField());
            int i2 = fieldOrder.indexOf(e2.getField());
            return Integer.compare(i1, i2);
          })
          // MessageSource を使ってメッセージを再解決する
          .map(fieldError -> messageSource.getMessage(fieldError, Locale.JAPAN))
          .collect(Collectors.toList());

      model.addAttribute("errList", errList);
      //--------------------------------------------------- ▲ End ▲
      return "register/form"; // 元の画面へ
    }
    // セッションスコープへ入力情報設定
    session.setAttribute("member", form);
    // 画面表示用
    model.addAttribute("member", form);
    // 確認画面へ
    return "register/confirm";
  }

  // 修正ボタン押下時の処理
  @PostMapping("/edit")
  public String edit(HttpSession session, Model model) {
    // セッションスコープより入力情報取得
    MemberForm menberForm = (MemberForm) session.getAttribute("member");
    // 画面表示用
    model.addAttribute("member", menberForm);
    return "register/form"; // 登録画面へ
  }

  // 登録ボタン押下時の処理
  @PostMapping("/register")
  public String register(HttpSession session, Model model) {
    // セッションスコープより入力情報取得
    MemberForm menberForm = (MemberForm) session.getAttribute("member");
    try {
      // 登録処理
      memberService.save(menberForm);
      // セッションスコープより登録情報削除
      session.removeAttribute("member");

      return "register/completed"; // 登録完了画面へ
    } catch (Exception e) {
      // エラーメッセージセット
      model.addAttribute("errMsg", e.getMessage());
      return "error/error"; // エラー画面へ
    }
  }
}
