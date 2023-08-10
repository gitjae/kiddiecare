<%--
  Created by IntelliJ IDEA.
  User: 채희재
  Date: 2023-08-10
  Time: 오후 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List</title>
</head>
<body>
    <section>
        <c:when test="${empty response}">
            <div class="data-notfund">
                <p>찾는 병원이 없어요..</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${response}" var="item">
                <div class="hospList">
                    <a>

                    </a>
                    <span>병원명 : ${item.yadmNm}</span>
                    <span>병원 주소: ${item.addr}</span>
                    <span>병원 번호: 미정</span>
                    <span>운영시간: 미정</span>
                    <span>휴진일: 미정</span>
                </div>
            </c:forEach>
        </c:otherwise>
    </section>
</body>
</html>
