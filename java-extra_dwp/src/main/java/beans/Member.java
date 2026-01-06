package beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Member implements Serializable {
  /** フィールド変数 */
  // 名前
  private String name;
  // 年齢
  private String age;
  // 性別
  private String gender;
  // 区分
  private String role;
  // 利用サービス
  private String[] services;
  // メール配信希望
  private String mailFlg;

  /** コンストラクター */
  // 引数なし
  public Member() {
  }

  // 引数あり
  public Member(String name, String age, String gender, String role, String[] services, String mailFlg) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.role = role;
    this.services = services;
    this.mailFlg = mailFlg;
  }

  /** フィールドメソッド */
  // getter/setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String[] getServices() {
    return services;
  }

  public void setServices(String[] services) {
    this.services = services;
  }

  public String getMailFlg() {
    return mailFlg;
  }

  public void setMailFlg(String mailFlg) {
    this.mailFlg = mailFlg;
  }

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
