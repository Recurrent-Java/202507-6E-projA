package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Customer;

/**
 * Servlet implementation class PreviewServlet
 */
@WebServlet("/PreviewServlet")
public class PreviewServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 送信されたデータのエンコーディングを指定（文字化け対策）
    request.setCharacterEncoding("UTF-8");
    // セッションスコープを取得
    HttpSession session = request.getSession();
    // 画面遷移先
    String url = "/WEB-INF/purchase/purchase-in.jsp";
    // セッションスコープよりユーザ情報取得
    Customer customer = (Customer) session.getAttribute("customer");
    // 未ログインの場合は商品購入不可
    if (customer == null) {
      request.setAttribute("errMsg", "商品を購入する場合は、ログインしてください。");
      url = "/WEB-INF/error/error.jsp";
    }

    // カート画面へ
    RequestDispatcher dis = request.getRequestDispatcher(url);
    dis.forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(request, response);
  }

}
