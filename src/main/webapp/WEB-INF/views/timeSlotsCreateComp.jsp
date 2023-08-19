<%--
  Created by IntelliJ IDEA.
  User: dldbs
  Date: 2023-08-19
  Time: 오후 1:31
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        *{
            /*margin: 0;*/
        }
        #container{
            height: 600px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        h1{
            text-align: center;
            margin-bottom: 20px;
            font-weight: bolder;
        }
        p{
            font-size: 20px;
            text-align: center;
        }


    </style>
    <title>예약 생성 완료</title>
</head>
<c:import url="headerIndex.jsp"></c:import>
<body>
    <div id="container">
        <div>
            <h1>[ ✔️ ]</h1>
            <h1>예약 생성이 완료되었습니다.</h1>
            <p>생성된 예약 정보는 <b>예약정보 확인</b> 에서 확인 가능합니다.</p>
        </div>
    </div>
</body>
</html>
