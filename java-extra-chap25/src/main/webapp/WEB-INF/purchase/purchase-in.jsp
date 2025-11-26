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
  <main id="cart">
    <c:choose>
      <c:when test="${ cart.size() > 0 }">
      <p>${ cart.size() }種類の商品があります。</p>
      </c:when>
      <c:otherwise>
      <p>カートに商品がありません。</p>
      </c:otherwise>
    </c:choose>
    <hr>
    <table>
      <tr>
        <th colspan="2">商品</th>
        <th>商品名</th>
        <th>価格</th>
        <th>個数</th>
      </tr>
      <c:forEach var="cart" items="${ cart }">
      <tr>
        <td>${cart.product.id }</td>
        <td><img src="${pageContext.request.contextPath}/images/${cart.product.id}.jpg" 
                 alt="${ cart.product.name }"></td>
        <td>${ cart.product.name }</td>
        <td><fmt:formatNumber value="${ cart.product.price }" type="number" /></td>
        <td>${ cart.count }個</td>
      </tr>
      </c:forEach>
    </table>
    <hr>
    <form action="${pageContext.request.contextPath}/PurchaseServlet" method="post">
     <p><label>お名前</label><input type="text" name="name"></p>
     <p><label>ご住所</label><input type="text" name="address"></p>
     <p><button type="submit" name="sendKind" value="regist">購入を確定</button></p>
    </form>
  </main>
  <%-- footer部の動的インクルード --%>
  <jsp:include page="/commonlayout/footer.jsp" />
</body>
</html>