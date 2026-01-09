package com.example.java_extra_spring.form;

import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class MemberForm {
  // 名前
  @NotBlank(message = "名前を入力してください。")
  private String name;
  // 年齢 (StringからIntegerに変更)
  // String型の時と異なり、NotBlankではなくNotNullを使用します
  @NotNull(message = "年齢を入力してください。")
  // 18歳未満のチェックを追加
  @Min(value = 18, message = "18歳未満の方はご利用できません。")
  private Integer age; 
  // 性別
  @NotBlank(message = "性別を入力してください。")
  private String gender;
  // 区分
  @NotBlank(message = "区分を選択してください。")
  @Pattern(regexp = "^[1-9][0-9]*$", message = "区分を選択してください。")
  private String role;

  private String[] services;

  private String mailFlg;
  

  /** 表示用メソッド */
  // 選択された gender を文字列にする
  public String showGenderText() {
    if (this.gender == null) {
      return "";
    }

    String text1 = this.gender.equals("male") ? "男性" : "";
    String text2 = this.gender.equals("female") ? "女性" : "";
    String text3 = this.gender.equals("other") ? "その他" : "";
    return text1 + text2 + text3;
  }

  // 選択された 区分 を文字列にする
  public String showRoleText() {
    if (this.role == null) {
      return "";
    }
    String result = switch (this.role) {
      case "1" -> "管理者";
      case "2" -> "講師";
      case "3" -> "受講生";
      default -> "";
    };
    return result;
  }

  // 利用サービスチェックメソッド
  public boolean checkServices(String service) {
    boolean result = false;
    if (this.services != null && this.services.length != 0) {
      result = Arrays.asList(this.services).contains(service);
    }
    return result;
  }

  // 選択された利用サービスを文字列にする
  public String showCheckedServicesText() {
    if (this.services == null || this.services.length == 0) {
      return "";
    }
    String result = Arrays.stream(this.services)
        .map(s -> switch (s) {
          case "1" -> "メール";
          case "2" -> "チャット";
          case "3" -> "ポータル";
          default -> "";
        })
        .collect(Collectors.joining("・"));

    return result;
  }

  // 選択された mailFlg を文字列にする
  public String showMailFlgText() {
    if (this.mailFlg == null) {
      return "";
    }

    String text1 = this.mailFlg.equals("yes") ? "希望する" : "";
    String text2 = this.mailFlg.equals("no") ? "希望しない" : "";
    return text1 + text2;
  }
}