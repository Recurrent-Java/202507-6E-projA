package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Customer;
import dao.CustomerDAO;

public class LoginAction extends Action {

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // セッションスコープ取得
    HttpSession session = request.getSession();
    // 送信種別の取得
    String sendKind = request.getParameter("sendKind");
    // 画面遷移先
    String url = null;
    if (sendKind == null || sendKind.isEmpty()) {
      // ログイン画面へ遷移
      url = "/WEB-INF/loginlogout/login-in.jsp";
    } else {
      // CustomerDAOインスタンスの生成
      CustomerDAO dao = new CustomerDAO();
      switch (sendKind) {
        // ログイン処理
        case "login":
          // 入力パラメーター取得
          // ログイン名
          String login = request.getParameter("login");
          // パスワード
          String password = request.getParameter("password");
          // 必須入力チェック
          if (login == null || login.isEmpty()) {
            // エラーをコントローラーへthrow
            throw new Exception("ログイン名が未入力です。");
          }
          if (password == null || password.isEmpty()) {
            // エラーをコントローラーへthrow
            throw new Exception("パスワードが未入力です。");
          }
          // ログイン情報取得
          Customer customer = dao.search(login, password);
          // セッションスコープへログイン情報セット
          session.setAttribute("customer", customer);
          // ログイン完了画面へ遷移
          url = "/WEB-INF/loginlogout/login-out.jsp";
          break;
        case "logout":
          // セッション破棄
          session.invalidate();
          url = "/WEB-INF/index.jsp";
          break;
        default:
          throw new Exception("もう一度初めから操作してください。");
      }
    }

    return url;
  }

}
