package bean;

import java.io.Serializable;

public class Product implements Serializable {
  /* フィールド変数 */
  // 商品ID
  private int id;
  // 商品名
  private String name;
  // 価格
  private int price;
  
  /* コンストラクター */
  // 引数なし
  public Product() { }
  // 引数あり
  public Product(int id, String name, int price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }
  /* フィールドメソッド */
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
   * @return name
   */
  public String getName() {
    return name;
  }
  /**
   * @param name セットする name
   */
  public void setName(String name) {
    this.name = name;
  }
  /**
   * @return price
   */
  public int getPrice() {
    return price;
  }
  /**
   * @param price セットする price
   */
  public void setPrice(int price) {
    this.price = price;
  }
  
  
}
