<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-08
  Time: 오후 2:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/css/join.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
    <script src="/script/join-api.js"></script>
</head>
<body>
    <div id="root">
        <section id="main-section">
            <div id="form">
                <div id="div-id">
                    <label for="id">아이디</label>
                    <input type="text" id="id" name="id">
                    <input type="button" id="duplChk" value="중복확인" onclick="idDuplChk()">
                </div>
                <div id="div-pw">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password">
                </div>
                <div id="div-pwChk">
                    <label for="passwordChk">비밀번호확인</label>
                    <input type="password" id="passwordChk" name="passwordChk">
                </div>
                <div id="div-name">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name">
                </div>
                <div id="div-birth">
                    <label for="birth">생년월일</label>
                    <input type="text" id="birth" name="birth">
                </div>
                <div id="div-gender">
                    <label id="label-gender">성별</label>

                    <label for="gender-m">남</label>
                    <input type="radio" id="gender-m" name="gender" value="m" checked>

                    <label for="gender-f">여</label>
                    <input type="radio" id="gender-f" name="gender" value="f">
                </div>
                <div id="div-phone">
                    <label for="phone">전화번호</label>
                    <input type="text" id="phone" name="phone">
                    <button id="send" onclick="sendCode()">인증번호발송</button>
                </div>
                <div id="div-code">
                    <input type="text" id="code" name="code">
                    <button id="verify" onclick="verify()">인증하기</button>
                </div>
                <div id="div-email">
                    <label for="email">이메일</label>
                    <input type="email" id="email" name="email">
                </div>

                <div class="form-group">
                    <input class="form-control" style="width: 40%; display: inline;" placeholder="우편번호" name="addr1" id="addr1" type="text" readonly="readonly" >
                    <button type="button" class="btn btn-default" onclick="execPostCode();"><i class="fa fa-search"></i> 우편번호 찾기</button>
                </div>
                <div class="form-group">
                    <input class="form-control" style="top: 5px;" placeholder="도로명 주소" name="addr2" id="addr2" type="text" readonly="readonly" />
                </div>
                <div class="form-group">
                    <input class="form-control" placeholder="상세주소" name="addr3" id="addr3" type="text"  />
                </div>

                <button id="submit" onclick="join()">가입하기</button>
            </div>
        </section>
    </div>
</body>
</html>
