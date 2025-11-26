<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- head部の動的インクルード --%>
<jsp:include page="/commonlayout/doctype.jsp" />
<body>
  <%-- header部の動的インクルード --%>
  <jsp:include page="/commonlayout/header.jsp" />
  <%-- nav部の動的インクルード --%>
  <jsp:include page="/commonlayout/nav.jsp" />
  <main>
    <h2>ログイン画面</h2>
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post" id="login">
      <p><label>ログイン名</label><input type="text" name="login"></p>
      <p><label>パスワード</label><input type="password" name="password"></p>
      <p><button type="submit" name="sendKind" value="login">ログイン</button></p>
    </form>
  </main>
  <%-- footer部の動的インクルード --%>
  <jsp:include page="/commonlayout/footer.jsp" />
</body>
</html>