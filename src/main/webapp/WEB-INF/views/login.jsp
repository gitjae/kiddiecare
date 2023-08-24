<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/login.css">
<%--    <link rel="stylesheet" href="/css/admin-login.css">--%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
    <script src="/script/apikeys.js"></script>
    <script src="/script/login-api.js"></script>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>--%>
    <script src="/script/admin-login.js"></script>
</head>
<body>
<div class="container">
    <section id="main-section">
        <a href="/">
            <h2 class="loginTitle">우리동네소아과</h2>
        </a>

        <div class="login-notice">
            <p class="notice-title">로그인 유형을 선택해주세요.</p>
            <button id="user-login-btn">일반회원</button>
            <button id="admin-login-btn">병원회원</button>
        </div>

        <%-- 유저 로그인 --%>
        <div id="form">
            <div class="wrapper">
                <div class="login-div">
                    <div id="div-id">
                        <label for="id">ID</label>
                        <input type="text" id="id" name="id" placeholder=" 아이디를 입력해주세요.">
                    </div>

                    <div id="div-pw">
                        <label for="password">PASSWORD</label>
                        <input type="password" id="password" name="password" placeholder=" 비밀번호를 입력해주세요.">
                    </div>

                    <div id="div-btn">
                        <button id="submit" onclick="login()">로그인</button>
                        <button id="findIdAndPw" onclick="gotofind()">아이디/비밀번호 찾기</button>
                    </div>

                    <div id="div-kakao">
                        <img src="/image/kakao_login.png" onclick="kakaoLogin()">
                    </div>

                    <div class="sign-up-area">
                        <p>아직 회원이 아니신가요?</p>
                        <a role="link"  href="/join" class="sign-up-a">회원가입</a>
                    </div>

                </div>
            </div>
        </div>

        <%-- 어드민 로그인 --%>
        <form class="login-form">
            <div class="wrapper">
                <div class="login-div">
                    <div class="div-id">
                        <div class="input-setting">
                            <label for="input-id" id="label-id">ID</label>
                            <input type="text" required placeholder="아이디를 입력해주세요." name="adminId" class="current-id"
                                   id="input-id">
                        </div>
                    </div>
                    <div class="div-pw">
                        <div class="input-setting">
                            <label for="input-pw" id="label-pw">PASSWORD</label>
                            <input type="password" required placeholder="비밀번호를 입력해주세요." name="adminPw"
                                   class="current-password"
                                   id="input-pw">
                        </div>
                    </div>
                    <div class="div-btn">
                        <div id="submit-btn-area">
                            <input type="button" id="submit-btn" value="로그인" onclick="checkValue(form)">
                        </div>
                    </div>
                    <div class="sign-up-area">
                        <p>아직 회원이 아니신가요?</p>
                        <a role="link" href="/admin/join" class="sign-up-a">회원가입</a>
                    </div>

                </div>
            </div>
        </form>

    </section>
</div>
</body>
</html>
