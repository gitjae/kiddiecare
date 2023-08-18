<%--
  Created by IntelliJ IDEA.
  User: regul
  Date: 2023-08-18
  Time: 오후 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/join.css">
    <title>Title</title>
</head>
<body>
<div class="container">
    <section id="main-section">
        <a href="/">
            <h2 class="loginTitle">우리동네소아과</h2>
        </a>
        <div id="form">
            <div class="wrapper">
                <div class="join-div">
                    <div id="div-id">
                        <label for="id">아이디</label>
                        <input type="text" id="id" name="id" value="${email}" readonly>
                        <button id="duplChk"onclick="idDuplChk()">중복확인</button>
                    </div>
                    <div id="div-pw">
                        <label for="password">비밀번호</label>
                        <input type="password" id="password" name="password" placeholder="한글, 영어 대소문자 [2~8자리 까지 입력 가능]">
                    </div>
                    <div id="div-pwChk">
                        <label for="passwordChk">비밀번호확인</label>
                        <input type="password" id="passwordChk" name="passwordChk" placeholder="보안을 위해 비밀번호를 다시 입력해주세요.">
                    </div>

                    <div id="div-name">
                        <label for="name">이름</label>
                        <input type="text" id="name" name="name" placeholder="보호자님의 이름을 입력해주세요.">
                    </div>
                    <div id="div-birth">
                        <label for="birth">생년월일</label>
                        <input type="text" id="birth" name="birth" placeholder="생년월일 8자리를 입력해주세요. (YYYYMMDD)">
                    </div>
                    <div id="div-gender">
                        <label id="label-gender">성별</label>

                        <label for="gender-m" style="text-align: right">남</label>
                        <input type="radio" id="gender-m" name="gender" value="m" checked>

                        <label for="gender-f" style="text-align: right">여</label>
                        <input type="radio" id="gender-f" name="gender" value="f">
                    </div>
                    <div id="div-phone">
                        <label for="phone">전화번호</label>
                        <input type="text" id="phone" name="phone" placeholder="전화번호를 입력해주세요. (01012345678)">
                        <button id="send" onclick="sendCode()">인증번호발송</button>
                    </div>
                    <div id="div-code">
                        <label>인증번호 확인</label>
                        <input type="text" id="code" name="code" placeholder="받으신 문자메세지의 인증번호를 입력해주세요.">
                        <button id="verify" onclick="verify()">인증하기</button>
                    </div>
                    <div id="div-email">
                        <label for="email">이메일</label>
                        <input type="email" id="email" name="email" value="${email}" readonly>
                    </div>
                    <div class="form-group">
                        <label>주소</label>
                        <input class="form-control" style="width: 40%; display: inline;" placeholder="우편번호 찾기 버튼을 선택해주세요." name="addr1"
                               id="addr1" type="text" readonly="readonly">
                        <button type="button" class="btn btn-default" onclick="execPostCode();"><i
                                class="fa fa-search"></i>
                            우편번호 찾기
                        </button>
                    </div>
                    <div class="form-group">
                        <label>도로명 주소</label>
                        <input class="form-control" style="top: 5px;" placeholder="우편번호 찾기를 선택하시면 자동으로 입력돼요." name="addr2" id="addr2"
                               type="text"
                               readonly="readonly"/>
                    </div>
                    <div class="form-group">
                        <label>상세주소</label>
                        <input class="form-control" placeholder="상세주소롤 작성해주세요." name="addr3" id="addr3" type="text"/>
                    </div>

                    <button id="submit" onclick="join()">가입하기</button>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/script/postcode.js"></script>
<script src="/script/kakao-join.js"></script>
</html>
