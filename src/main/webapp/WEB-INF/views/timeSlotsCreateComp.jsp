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
            text-align: center;
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

        button{
            margin-top: 50px;
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
            <p>기존에 등록되어있던 예약과 겹치면, 해당 예약은 기존 예약으로 대체됩니다.</p>
            <p>생성된 예약 정보는 <b>예약정보 확인</b> 에서 확인해주세요.</p>
            <button onclick="location.href='/admin/index'">홈으로가기</button>
        </div>
    </div>
</body>
</html>
