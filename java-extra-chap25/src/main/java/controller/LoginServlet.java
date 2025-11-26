package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import action.Action;
import action.LoginAction;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // 送信されたデータのエンコーディングを指定（文字化け対策）
    //request.setCharacterEncoding("UTF-8");
    // セッションスコープの取得
    //HttpSession session = request.getSession();
    // セッション破棄
    //session.invalidate();
    // ログイン画面へ
    //RequestDispatcher dis = request.getRequestDispatcher("/WEB-INF/loginlogout/login-in.jsp");
    //dis.forward(request, response);
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
      // LoginActionのインスタンス化
      Action action = new LoginAction();
      url = action.execute(request, response);
     
    }catch(Exception e) {
      e.printStackTrace();
      // リクエストスコープへエラーメッセージセット
      request.setAttribute("errMsg", "ログインエラー：<br>" + e.getMessage());
    }
    /* 画面遷移 */
    RequestDispatcher dis = request.getRequestDispatcher(url);
    dis.forward(request, response);
	}

}
