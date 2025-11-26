<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- head部の動的インクルード --%>
<jsp:include page="/commonlayout/doctype.jsp" />
<body>
  <%-- header部の動的インクルード --%>
  <jsp:include page="/commonlayout/header.jsp" />
  <%-- nav部の動的インクルード --%>
  <jsp:include page="/commonlayout/nav.jsp" />
  <main>
  <main>
    <h2>ログイン完了</h2>
    <p>こんにちは、${ customer.login }さん。</p>
  </main>
  <%-- footer部の動的インクルード --%>
  <jsp:include page="/commonlayout/footer.jsp" />
</body>
</html>