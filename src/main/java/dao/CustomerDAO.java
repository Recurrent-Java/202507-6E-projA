package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Customer;

public class CustomerDAO {
  public Customer search(String login, String password) throws Exception {
    // リターン情報
    Customer customer = null;
    // SQL文
    String sql = """
        SELECT
          id
        , login
        , password
        FROM
          customer
        WHERE
          login = ?
        AND
          password = ?
        """;
    try (Connection conn = DBManager.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)) {
      // ログイン名とパスワードを設定
      pst.setString(1, login);
      pst.setString(2, password);

      // クエリ発行
      ResultSet rs = pst.executeQuery();

      // DBデータの取り出し&Customerインスタンスの生成
      if (rs.next()) {
        //  DBデータの取り出し
        int did = rs.getInt("id");
        String dlogin = rs.getString("login");
        String dpassword = rs.getString("password");
        // Customerインスタンスの生成
        customer = new Customer(did, dlogin, dpassword);
      } else {
        // 検索データがみつからなかったため、エラーをアクションへthrow
        throw new Exception("ログイン名またはパスワードが違います。");
      }
    }catch(SQLException sqe) {
      // DBエラーのため、エラーをアクションへthrow
      throw new Exception("管理者へご連絡願います。<br>" + sqe.getMessage());
    }
    return customer;
  }
}
