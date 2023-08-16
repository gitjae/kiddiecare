<%--
    Created by IntelliJ IDEA.
    User: 집
    Date: 2023-08-13
    Time: 오후 12:43
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>병원 등록</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/admin-join.css">
</head>
<body>
<c:import url="header.jsp"></c:import>
<section>
    <form method="POST" action="">
        <h1>병원 관계자 회원가입</h1>
        <div class="input-box">
            <input type="text" id="hosp-name" name="hosp-name" placeholder="병원명" readonly />
            <input type="button" id="hosp-name-btn" name="hosp-name-btn" value="병원 찾기" onclick="showModal()" />
            <div class="err-box">
                <span class="err" id="hosp-name-null">병원명과 병원주소를 입력하세요.</span>
            </div>
        </div>
        <div id="myModal" class="modal">
            <div class="modal-content">
                <div class="text-area">
                    <input type="text" id="hosp-search" name="hosp-search" placeholder="병원명을 입력해주세요"/>
                    <input type="button" id="hosp-search-btn" name="hosp-search-btn" value="병원 확인" onclick="searchHospName()" />
                    <span class="close" onclick="closeModal()">&times;</span>
                </div>
                <div class="hosp-list-area">
                    <div class="spinner-border" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                    <div class="hosp-list">

                    </div>
                </div>
            </div>
        </div>
        <div class="input-box">
            <input type="text" id="hosp-address" name="hosp-address" placeholder="병원 주소" readonly />
        </div>
        <div class="input-box">
            <input type="text" id="admin-id" name="admin-id" placeholder="아이디" />
            <input type="button" id="admin-id-btn" name="admin-id-btn" value="중복 확인" onclick="chkAdminId()" />
            <div class="err-box">
                <span class="err" id="admin-id-null">아이디를 입력해주세요.</span>
                <span class="err" id="admin-id-dupl">이미 사용중인 아이디입니다.</span>
            </div>
        </div>
        <div class="input-box">
            <input type="password" id="admin-pw" name="admin-pw" placeholder="비밀번호" />
            <div class="err-box">
                <p class="err">*특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.</p>
                <span class="err" id="chk-admin-pw">비밀번호를 입력해주세요.</span>
                <span class="err" id="not-pw-format">*특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.</span>
            </div>
        </div>
        <div class="input-box">
            <input type="password" id="admin-pw-ch" name="admin-pw" placeholder="비밀번호 확인" />
            <div class="err-box">
                <span class="err" id="chk-admin-pw-again">비밀번호 확인을 입력 해주세요.</span>
                <span class="err" id="chk-admin-pw-same">비밀번호가 같지 않습니다.</span>
                <span class="err" id="not-pw-again-format">*특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.</span>
            </div>
        </div>
        <div class="input-box">
            <input type="email" id="admin-email" name="admin-email" placeholder="이메일" />
            <input type="button" id="admin-email-btn" name="admin-email-btn" value="이메일 인증" onclick="sendAuthToken()" />
            <div class="err-box">
                <span class="err" id="admin-email-null"> 이메일을 입력해주세요. </span>
                <span class="err" id="not-email-format">올바른 이메일 형식이 아닙니다.</span>
            </div>
        </div>
        <div class="input-box">
            <input type="email" id="auth-code" name="admin-email" placeholder="이메일 인증번호" />
            <input type="button" id="chk-auth-code-btn" name="admin-email-btn" value="인증번호 확인" onclick="checkAuthToken()" />
            <div class="err-box">
                <span class="err" id="chk-email"></span>
                <span class="err" id="chkMsgEmail" size="1"></span>
            </div>
        </div>
        <div class="input-box">
            <input type="text" id="admin-name" name="admin-name" placeholder="이름" />
            <div class="err-box">
                <span class="err" id="admin-name-null">이름을 입력해주세요.</span>
                <span class="err" id="chk-admin-name">3자 이상 영어,한글로 작성해주세요.</span>
            </div>
        </div>
        <div class="input-box">
            <label for="docter-name">원장의사님</label>
            <input type="text" id="docter-name" name="admin-attach" />
            <div class="err-box">
                <span class="err" id="docter-name-null">증빙자료를 제공해주세요.</span>
            </div>
        </div>
        <div class="input-box">
            <label for="admin-attach">의료기관 개설 허가증/의사 면허/사업자 등록증 첨부</label>
            <input type="file" id="admin-attach" name="admin-attach" />
            <div class="err-box">
                <span class="err" id="admin-file-null">증빙자료를 제공해주세요.</span>
            </div>
        </div>
        <input type="button" id="join-btn" value="회원 가입" onclick="checkValue(form)" />
    </form>
</section>
<script src="/script/admin-join.js"></script>
</body>
</html>
