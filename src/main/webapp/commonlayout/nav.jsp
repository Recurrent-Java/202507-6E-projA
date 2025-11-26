 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <nav>
    <ul>
      <li><a href="${pageContext.request.contextPath}/ProductServlet">商品</a></li>
      <li><a href="${pageContext.request.contextPath}/CartServlet">カート</a></li>
      <li><a href="${pageContext.request.contextPath}/PreviewServlet">購入</a></li>
      <li><a href="${pageContext.request.contextPath}/LoginServlet?sendKind=${ empty customer ? '' : 'logout' }">${ empty customer ? 'ログイン' : 'ログアウト' }</a></li>
    </ul>
  </nav>