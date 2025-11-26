package action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Item;
import bean.Product;
import dao.ProductDAO;

public class ProductAction extends Action {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // セッションスコープの取得
    HttpSession session = request.getSession();
    // 送信種別を取得
    String sendKind = request.getParameter("sendKind");
    // 遷移先URL
    String url = null;
    // カート
    List<Item> cart = null;
    // 商品リスト
    List<Product> productList = null;
    /** 送信種別により処理を分岐する */
    if (sendKind == null || sendKind.isEmpty()) {
      // 商品検索画面へ遷移
      url = "/WEB-INF/product/product.jsp";
    } else {
      switch (sendKind) {
        // 商品検索
        case "find":
          // リクエストパラメーターの取得
          // 検索ワード
          String keyword = request.getParameter("keyword");
          if (keyword == null || keyword.isEmpty()) {
            keyword = "";
          }
          // ProductDAOのインスタンス化
          ProductDAO dao = new ProductDAO();
          // 商品データ取得
          List<Product> list = dao.search(keyword);
          // セッションスコープに商品データセット
          session.setAttribute("list", list);
          // 商品検索画面へ遷移
          url = "/WEB-INF/product/product.jsp";
          break;
        // 商品追加
        case "add":
          // リクエストパラメータの取得
          int id = Integer.parseInt(request.getParameter("id"));
          // カートをセッションスコープより取得
          cart = (List<Item>)session.getAttribute("cart");
          // カートのなかみをチェック
          // 最初のカート追加
          if(cart == null) {
            // カートを生成
            cart = new ArrayList<>();
            // セッションスコープより商品情報取得
            productList = 
                (List<Product>)session.getAttribute("list");
            // 追加する商品情報を取得
            Product product = new Product();
            for(Product pro: productList) {
              if(pro.getId() == id) {
                product = new Product(pro.getId(), pro.getName(),pro.getPrice());
              }
            }
            // Itemインスタンスを生成
            Item item = new Item(product,1);
            // カートに追加
            cart.add(item);
          }
          // 2回目以降のカートの追加
          else {
            // カートの中のProductオブジェクトのidと追加しようとしているidが等しいかを検索
            boolean exists = cart.stream()
                .anyMatch(item -> item.getProduct().getId() == id);
            // 既にカートに存在する商品の追加
            if(exists) {
              cart.stream()
              .filter(item -> item.getProduct().getId() == id)
              .findFirst()
              .ifPresent(item -> item.setCount(item.getCount() + 1));
            }
            // 新規に商品を追加する
            else {
              // セッションスコープより商品情報取得
              productList = 
                  (List<Product>)session.getAttribute("list");
              // 追加する商品情報を取得
              Product product = new Product();
              for(Product pro: productList) {
                if(pro.getId() == id) {
                  product = new Product(pro.getId(), pro.getName(),pro.getPrice());
                }
              }
              // Itemインスタンスを生成
              Item item = new Item(product,1);
              // カートに追加
              cart.add(item);             
            }
          }
          // セッションスコープにカートをセット
          session.setAttribute("cart", cart);
          // 画面遷移先
          url = "/WEB-INF/product/cart.jsp";
          break;
        // 商品削除
        case "remove":
          // リクエストパラメータの取得
          int did = Integer.parseInt(request.getParameter("id"));
          // カートをセッションスコープより取得
          cart = (List<Item>)session.getAttribute("cart");
          // 該当の商品を検索
          Optional<Item> resultItem = 
              cart.stream()
              .filter(it -> it.getProduct().getId() == did)
              .findFirst();
          // 商品の削除処理
          if(resultItem.isPresent()) {
            Item item = resultItem.get();
            // 個数が2以上の場合、商品の購入個数を-1
            if(item.getCount() >= 2) {
              item.setCount(item.getCount()-1);
            }
            // 個数が2未満の場合、商品削除
            else {
              cart.remove(item);
            }
          }
          // セッションスコープにカートをセット
          session.setAttribute("cart", cart);
          // 画面遷移先
          url = "/WEB-INF/product/cart.jsp";
          break;
      }
    }
    return url;
  }

}
