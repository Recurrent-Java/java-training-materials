package beans;

import java.io.Serializable;
import java.util.Arrays;

/* 入力情報管理クラス */

public class Question implements Serializable {
  // 名前
  private String name;
  // 年齢
  private String age;
  // 性別
  private String gender;
  // 使用しているパソコンのOS
  private String[] os;
  // 好きな色
  private String color;
  // ご意見・ご要望
  private String comment;

  /**
   * 引数なしのコンストラクタ
   */
  public Question() {}

  /**
   * コンストラクタ
   * @param name    名前
   * @param age     年齢
   * @param gender  性別
   * @param os      使用しているパソコンのOS
   * @param color   好きな色
   * @param comment ご意見・ご要望 
   */
  public Question(String name, String age, String gender, String[] os, String color, String comment) {
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.os = os;
    this.color = color;
    this.comment = comment;
  }

  // getter/setter
  /**
   * @return name 戻す名前
   */
  public String getName() {
    return name;
  }

  /**
   * @param name セットする名前
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return age 戻す年齢
   */
  public String getAge() {
    return age;
  }

  /**
   * @param age セットする年齢
   */
  public void setAge(String age) {
    this.age = age;
  }

  /**
   * @return gender 戻す性別
   */
  public String getGender() {
    return gender;
  }

  /**
   * @param gender セットする性別
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

  /**
   * @return os 戻す使用しているパソコンのOS
   */
  public String[] getOs() {
    return os;
  }

  /**
   * @param os セットする使用しているパソコンのOS
   */
  public void setOs(String[] os) {
    this.os = os;
  }

  /**
   * @return color 戻す好きな色
   */
  public String getColor() {
    return color;
  }

  /**
   * @param color セットする好きな色
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * @return comment 戻すご意見・ご要望
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment セットするご意見・ご要望
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  // 選択された gender を文字列にする (JSP 表示用)
  public String showGenderText() {
    if (this.gender == null) {
      return "";
    }

    String text1 = this.gender.equals("male") ? "男性" : "";
    String text2 = this.gender.equals("female") ? "女性" : "";
    return text1 + text2;
  }

  // OSの存在チェックメソッド
  public boolean checkOs(String os) {
    boolean result = false;
    if (this.os != null && this.os.length != 0) {
      result = Arrays.asList(this.os).contains(os);
    }
    return result;
  }

  // 選択されたOSを文字列にする (JSP 表示用)
  public String showCheckedOsText() {
    if (this.os == null || this.os.length == 0) {
      // 未選択の場合
      return "";
    }

    String osText = "";
    for (String checkedOs : this.os) {
      osText += toOsText(checkedOs) + " ";
    }
    return osText;
  }

  // 選択された色を文字列にする (JSP 表示用)
  public String showSelectedColorText() {
    if (this.color == null || this.color.length() == 0) {
      // 未選択の場合
      return "";
    }

    switch (this.color) {
      case "red":
        return "赤";
      case "blue":
        return "青";
      case "yellow":
        return "黄";
      default:
        return "";
    }
  }

  private String toOsText(String os) {
    switch (os) {
      case "win":
        return "Windows";
      case "mac":
        return "Macintosh";
      case "linux":
        return "Linux";
      default:
        return "";
    }
  }
}
