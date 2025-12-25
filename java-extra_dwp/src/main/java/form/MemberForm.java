package form;

import java.util.Arrays;

public class MemberForm {
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
  public MemberForm(String name, String age, String gender, String role, String[] services, String mailFlg) {
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
  // 利用サービスチェックメソッド
  public boolean checkServices(String service) {
    boolean result = false;
    if (this.services != null && this.services.length != 0) {
      result = Arrays.asList(this.services).contains(service);
    }
    return result;
  }
}
