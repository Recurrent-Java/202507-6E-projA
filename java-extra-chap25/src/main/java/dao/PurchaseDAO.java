package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import bean.Item;
import bean.Product;

public class PurchaseDAO {
  public void insert(List<Item> cart,
      String name, String address) throws Exception {
    try (Connection conn = DBManager.getConnection()) {
      // Auto-commit OFF
      conn.setAutoCommit(false);
      // SQL文
      String sql = """
              INSERT INTO purchase(
                product_id
              , product_name
              , product_price
              , product_count
              , customer_name
              , customer_address)
              VALUES (
                ?
              , ?
              , ?
              , ?
              , ?
              , ?)
          """;
      for (Item item : cart) {
        PreparedStatement pst = conn.prepareStatement(sql);
        Product product = item.getProduct();
        // 可変要素を設定
        pst.setInt(1, product.getId());
        pst.setString(2, product.getName());
        pst.setInt(3, product.getPrice());
        pst.setInt(4, item.getCount());
        pst.setString(5, name);
        pst.setString(6, address);
        // クエリの発行
        int result = pst.executeUpdate();
        // PreparedStatementをクローズ
        pst.close();
        // 登録チェック
        if (result != 1) {
          // ロールバック
          conn.rollback();
          // Auto-commit ON
          conn.setAutoCommit(true);
          throw new Exception("購入処理が失敗しました。");
        }
      }
      // 登録処理完了
      conn.commit();
      // Auto-commit ON
      conn.setAutoCommit(true);
    } catch (SQLException sqe) {
      throw new Exception("データベースエラー：<br>" + sqe.getMessage());
    }
  }
}
