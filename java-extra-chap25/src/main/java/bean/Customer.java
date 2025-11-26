package bean;

import java.io.Serializable;

public class Customer implements Serializable {
  /** フィールド変数 */
  // ユーザID
  private int id;
  // ユーザ名
  private String login;
  // パスワード
  private String password;
  /** コンストラクター */
  // 引数なし
  public Customer() {
    // JavaBeansのルールに従い作成
  }
  // 引数あり
  public Customer(int id, String login, String password) {
    this.id = id;
    this.login = login;
    this.password = password;
  }
  /** フィールドメソッド */
  // getter/setter
  /**
   * @return id
   */
  public int getId() {
    return id;
  }
  /**
   * @param id セットする id
   */
  public void setId(int id) {
    this.id = id;
  }
  /**
   * @return login
   */
  public String getLogin() {
    return login;
  }
  /**
   * @param login セットする login
   */
  public void setLogin(String login) {
    this.login = login;
  }
  /**
   * @return password
   */
  public String getPassword() {
    return password;
  }
  /**
   * @param password セットする password
   */
  public void setPassword(String password) {
    this.password = password;
  }
  
}
