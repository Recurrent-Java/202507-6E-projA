package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import action.Action;
import action.PurchaseAction;
import bean.Customer;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 送信されたデータのエンコーディングを指定（文字化け対策）
    request.setCharacterEncoding("UTF-8");
    // 画面遷移先(初期値をエラー画面に設定)
    String url = "/WEB-INF/error/error.jsp";
    try {
      // セッションスコープの取得
      HttpSession session = request.getSession();
      // セッションスコープよりユーザ情報取得
      Customer customer = (Customer)session.getAttribute("customer");
      // 未ログインの場合は商品購入不可
      if (customer == null) {
        throw new Exception("商品を購入する場合は、ログインしてください。");
      }         
      // PurchaseActionのインスタンス化
      Action action = new PurchaseAction();
      url = action.execute(request, response);
     
    }catch(Exception e) {
      e.printStackTrace();
      // リクエストスコープへエラーメッセージセット
      request.setAttribute("errMsg", "商品購入エラー：<br>" + e.getMessage());
    }
    /* 画面遷移 */
    RequestDispatcher dis = request.getRequestDispatcher(url);
    dis.forward(request, response);
	}

}
