<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- head部の動的インクルード --%>
<jsp:include page="/commonlayout/doctype.jsp" />
<body>
  <%-- header部の動的インクルード --%>
  <jsp:include page="/commonlayout/header.jsp" />
  <%-- nav部の動的インクルード --%>
  <jsp:include page="/commonlayout/nav.jsp" />
  <main>
    <h2>エラー発生</h2>
    <p>${ errMsg }</p>
  </main>
  <%-- footer部の動的インクルード --%>
  <jsp:include page="/commonlayout/footer.jsp" />
</body>
</html>