/**
 *
 * DBマネージャー
 * @since  : 1.0 : 2025/11/11
 *
 * Copyright (c) Recurrent.<br>
 *
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* データベースの接続を行う
*
* @since  : 2025/11/11
* @author : K.Imazawa
*
*/
public class DBManager {

  private static final String NAME_DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
  private static final String URL = "jdbc:mysql://localhost:3306/extradb?characterEncoding=UTF-8&serverTimeZone=JST";
  private static final String USER = "recurrent6e";
  private static final String PASSWORD = "6e202507";
  /**
  *
  * コネクションを確立します
  *
  * @since  : 2025/11/11 K.Imazawa
  *
  * @return  Connection コネクション
  *
  * @throws SQLException DBのオープンに失敗した場合
  */
  public static Connection getConnection() throws SQLException {
    Connection conn = null;
    try {
      Class.forName(NAME_DRIVER_MYSQL);
      conn = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return conn;
  }
}
