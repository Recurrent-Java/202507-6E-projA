package bean;

import java.io.Serializable;

public class Item implements Serializable {
  /** フィールド変数 */
  // Productインスタンス
  private Product product;
  // 購入数
  private int count;
  
  /** コンストラクター */
  // 引数ない
  public Item() {
    // JavaBeansのルールに従い作成
  }
  // 引数あり
  public Item(Product product, int count) {
    this.product = product;
    this.count = count;
  }
  
  /** フィールドメソッド */
  // getter/setter
  /**
   * @return product
   */
  public Product getProduct() {
    return product;
  }
  /**
   * @param product セットする product
   */
  public void setProduct(Product product) {
    this.product = product;
  }
  /**
   * @return count
   */
  public int getCount() {
    return count;
  }
  /**
   * @param count セットする count
   */
  public void setCount(int count) {
    this.count = count;
  }
  
}
