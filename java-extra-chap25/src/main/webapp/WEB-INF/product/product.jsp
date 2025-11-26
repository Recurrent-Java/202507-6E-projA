<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib  prefix="fmt" uri="jakarta.tags.fmt"%>
<%-- head部の動的インクルード --%>
<jsp:include page="/commonlayout/doctype.jsp" />
<body>
  <%-- header部の動的インクルード --%>
  <jsp:include page="/commonlayout/header.jsp" />
  <%-- nav部の動的インクルード --%>
  <jsp:include page="/commonlayout/nav.jsp" />
  <main id="product">
    <p>検索キーワードを入力して下さい。</p>
    <form action="${pageContext.request.contextPath}/ProductServlet" method="post">
      <p>
        <label>商品名：</label><input type="text" name="keyword">　
        <button type="submit" name="sendKind" value="find">検索</button>
      </p>
    </form>
    <hr>
    <h2>商品一覧</h2>
    <c:if test="${ not empty errMsg }">
    <p>${ errMsg }</p>
    </c:if>
    <table>
      <tr>
        <th colspan="2">商品</th>
        <th>商品名</th>
        <th>価格</th>
        <th>購入</th>
      </tr>
      <c:forEach var="product" items="${ list }">
      <tr>
        <td>${product.id }</td>
        <td><img src="${pageContext.request.contextPath}/images/${product.id}.jpg" 
                 alt="${ product.name }"></td>
        <td>${ product.name }</td>
        <td><fmt:formatNumber value="${ product.price }" type="number" /></td>
        <td>
          <a href="${pageContext.request.contextPath}/ProductServlet?id=${product.id}&sendKind=add">
          カートに追加
          </a>
        </td>
      </tr>
      </c:forEach>
    </table>
  </main>
  <%-- footer部の動的インクルード --%>
  <jsp:include page="/commonlayout/footer.jsp" />
</body>
</html>