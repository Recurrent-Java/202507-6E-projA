package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Product;

public class ProductDAO {
  // 商品検索
  public List<Product> search(String keyword) throws Exception {
    // リスト生成
    List<Product> productList = new ArrayList<>();
    // SQL文作成
    String sql = "SELECT id, name, price FROM product WHERE name LIKE ?";
    // DB接続
    // クエリ発行の準備
    try (Connection conn = DBManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {

      ps.setString(1, "%" + keyword + "%");
      // クエリの発行
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        // 商品ID取り出し
        int pid = rs.getInt("id");
        // 商品名取り出し
        String name = rs.getString("name");
        // 価格取り出し
        int price = rs.getInt("price");
        // Productインスタンスの生成
        Product product = new Product(pid, name, price);
        // リストに追加
        productList.add(product);
      }
    } catch (SQLException sqe) {
      throw new Exception("データベースエラー：" + sqe.getMessage());
    }
    return productList;
  }
}
