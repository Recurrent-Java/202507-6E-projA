package action;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Customer;
import bean.Item;
import dao.PurchaseDAO;

public class PurchaseAction extends Action {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // セッションスコープを取得
    HttpSession session = request.getSession();
    // ログイン情報取得
    Customer customer = (Customer) session.getAttribute("customer");
    // 未ログインの場合は商品購入不可
    if (customer == null) {
      throw new Exception("商品を購入する場合は、ログインしてください。");
    }
    // リクエストパラメーターの取得
    // 名前
    String name = request.getParameter("name");
    // 住所
    String address = request.getParameter("address");
    // 必須入力チェック
    if (name == null || name.isEmpty()) {
      throw new Exception("名前が未入力です。");
    }
    if (address == null || address.isEmpty()) {
      throw new Exception("住所が未入力です。");
    }
    // セッションスコープよりカートを取得
    List<Item> cart = (List<Item>) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) {
      throw new Exception("購入する商品が選択されていません。");
    }
    // PurchaseDAOのインスタンス化
    PurchaseDAO dao = new PurchaseDAO();
    // 登録処理
    dao.insert(cart, name, address);

    // セッションよりカートの情報を削除
    session.removeAttribute("cart");

    return "/WEB-INF/purchase/purchase-out.jsp";

  }

}
