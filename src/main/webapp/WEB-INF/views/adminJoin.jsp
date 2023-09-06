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
    <link rel="shortcut icon" href="/image/favicon.ico">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/admin-join.css">
</head>
<body>
<c:import url="header.jsp"></c:import>
<section>
    <div id="background-area">
        <div id="main-section">

            <form method="POST" action="/admin/info/join" enctype="multipart/form-data">
                <h1 id="title">병원 관계자 회원가입</h1>

                <div class="input-box">
                    <label for="hosp-name">병원명 </label>
                    <input type="text" id="hosp-name" name="hospitalName" placeholder="병원찾기 버튼을 눌러 병원을 조회하세요" readonly />
                    <input type="button" id="hosp-name-btn" class="button-style" name="hosp-name-btn" value="병원 찾기" onclick="showModal()" />
                    <div class="err-box">
                        <span class="err" id="hosp-name-null">병원찾기로 병원을 조회하세요.</span>
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
                            <div class="container-spinner">
                                <div class="spinner"></div>
                            </div>
                            <div class="hosp-list"></div>
                        </div>
                    </div>
                </div>
                <div class="input-box">
                    <label for="hosp-address">병원주소 </label>
                    <input type="text" id="hosp-address" name="hosp-address" placeholder="병원 주소가 표시됩니다." readonly />
                    <input type="hidden" id="hosp-ykiho" name="ykiho">
                </div>
                <div class="input-box">
                    <label for="admin-id">아이디 </label>
                    <input type="text" id="admin-id" name="adminId" placeholder="아이디를 입력해주세요." />
                    <input type="button" id="admin-id-btn" class="button-style" name="admin-id-btn" value="중복 확인" onclick="chkAdminId()" />
                    <div class="err-box">
                        <span class="err" id="admin-id-err"></span>
                        <span class="err" id="admin-id-null">아이디를 입력해주세요.<br></span>
                        <span class="err" id="admin-id-check">아이디 확인을 해주세요.<br></span>
                        <span class="err" id="admin-id-dupl">이미 사용중인 아이디입니다.<br></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="admin-pw">비밀번호 </label>
                    <input type="password" id="admin-pw" name="adminPw" placeholder="비밀번호를 입력해주세요." />
                    <div class="err-box">
                        <p class="err">*특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.<br></p>
                        <span class="err" id="chk-admin-pw">비밀번호를 입력해주세요.<br></span>
                        <span class="err" id="not-pw-format">특수문자는 '! @ # $ % ^ & +='만 사용 가능합니다.<br></span>
                        <span class="err" id="chkNotice1"></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="admin-pw-ch">비밀번호 확인 </label>
                    <input type="password" id="admin-pw-ch" name="admin-pw" placeholder="비밀번호를 동일하게 입력해주세요." />
                    <div class="err-box">
                        <span class="err" id="chk-admin-pw-again">비밀번호 확인을 입력 해주세요.<br></span>
                        <span class="err" id="chkNotice2"></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="admin-email">이메일 </label>
                    <input type="text" id="admin-email" name="adminEmail" placeholder="이메일을 입력해주세요." />
                    <input type="button" id="admin-email-btn" class="button-style" name="admin-email-btn" value="이메일 인증" onclick="sendAuthToken()" />
                    <div class="err-box">
                        <span class="err" id="admin-email-null"> 이메일을 입력해주세요.<br></span>
                        <span class="err" id="not-email-format">올바른 이메일 형식이 아닙니다.<br></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="auth-code">이메일 인증번호 </label>
                    <input type="email" id="auth-code" name="auth-code" placeholder="이메일 인증번호를 입력해주세요." />
                    <input type="button" id="chk-auth-code-btn" class="button-style" name="admin-email-btn" value="인증번호 확인" onclick="checkAuthToken()" />
                    <div class="err-box">
                        <span class="err" id="chk-email"> 이메일 인증을 해주세요.<br></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="admin-name">관리자 이름</label>
                    <input type="text" id="admin-name" name="adminName" placeholder="병원 관계자 이름을 입력해주세요." />
                    <div class="err-box">
                        <span class="err" id="admin-name-null">이름을 입력해주세요.<br></span>
                        <span class="err" id="chkNotice3"></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="doctor-name">원장의사 이름</label>
                    <input type="text" id="doctor-name" name="doctorName" placeholder="원장의사 이름을 입력해주세요." />
                    <div class="err-box">
                        <span class="err" id="doctor-name-null">의사정보를 제공해주세요.<br></span>
                        <span class="err" id="chkNotice4"></span>
                    </div>
                </div>
                <div class="input-box">
                    <label for="admin-attach">의료기관 개설 허가증/의사 면허/사업자 등록증 첨부</label>
                    <input type="file" id="admin-attach" name="file" accept=".png,.jpg" size="10000000"/>
                    <div class="err-box">
                        <span class="err" id="admin-file-null">증빙자료를 제공해주세요.</span>
                    </div>
                </div>
                <div class="select-area">
                    <label for="agree_all">
                        <input type="checkbox" name="agree_all" id="agree_all">
                        <span>모두 동의합니다</span>
                    </label>
                    <label for="agree1">
                        <input type="checkbox" name="agree" id="agree1" value="1">
                        <span>이용약관 동의<strong>(필수)</strong></span>
                    </label>
                    <label for="agree2">
                        <input type="checkbox" name="agree" id="agree2" value="2">
                        <span>개인정보 수집, 이용 동의<strong>(필수)</strong></span>
                    </label>
                    <label for="agree3">
                        <input type="checkbox" name="agree" id="agree3" value="3">
                        <span>개인정보 이용 동의<strong>(필수)</strong></span>
                    </label>
                    <label for="agree4">
                        <input type="checkbox" name="agree" id="agree4" value="4">
                        <span>이벤트, 혜택정보 수신동의<strong class="select_disable">(선택)</strong></span>
                    </label>
                </div>
                <input type="button" id="join-btn" class="button-style" value="회원 가입" onclick="checkValue(form)" />
            </form>

        </div>
    </div>

</section>
<script src="/script/admin-join.js"></script>
</body>
</html>
