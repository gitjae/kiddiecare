<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%-- MainController -> @GetMapping("adminIndex") --%>
<head>
    <title>우리동네소아과 관리자 페이지</title>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/admin-index.css">
</head>
<c:import url="headerIndex.jsp"></c:import>
<body>
<div class="container">
  <section id="section">
      <div class="mypage-title">
          <p>관리자 페이지</p>
      </div>
      <div class="content-wrapper">
          <aside id="sidebar">
              <div id="bar-nav">
                  <ul id="ul-nav">
                      <li>
                          <div id="create-schedule" name="div-schedule" onclick="sectionChange(this)">
                              스케줄 생성
                          </div>
                      </li>
                      <li>
                          <div id="appo-status" name="div-appo" onclick="sectionChange(this)">
                              예약자 현황
                          </div>
                      </li>
                      <li>
                          <div id="notice" name="div-notice" onclick="sectionChange(this)">
                              공지사항 설정
                          </div>
                      </li>
                      <li>
                          <div id="userInfo-change" name="div-userInfo" onclick="sectionChange(this)">
                              회원정보 변경
                          </div>
                      </li>
                  </ul>
              </div>
          </aside>
          <div id="main-section">
              <div class="main-div" id="div-user">
<%--                    본문 내용 --%>
              </div>
          </div>
      </div>
  </section>
</div>
</body>
<c:import url="footer.jsp"></c:import>
</html>
