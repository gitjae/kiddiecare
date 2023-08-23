<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%-- MainController -> @GetMapping("adminIndex") --%>
<head>
    <title>우리동네소아과 관리자 페이지</title>
    <link href="/css/common.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/admin-index.css">
</head>
<c:choose>
    <c:when test="${empty Ykiho}">
        <c:url var="myUrl" value="/admin/login"/>
        <c:redirect url="${myUrl}"></c:redirect>
    </c:when>
    <c:otherwise>
<c:import url="headerIndex.jsp"></c:import>
<body>
<div class="container">
  <section id="section">
      <div class="content-wrapper">
          <aside id="sidebar">
              <div id="bar-nav">
                  <div id="log-area">
                      <input type="text" id="ykiho" name="ykiho" placeholder="병원코드" value="${Ykiho}" style="display: none">
                      <p><span><b>${log}님 반갑습니다.</b></span></p>
                      <span id="hospital_name"></span>
                  </div>
                  <ul id="ul-nav">
                      <li>
                          <div id="create-schedule" name="div-schedule" onclick="contentChange(this)">
                              스케줄 생성
                          </div>
                      </li>
                      <li>
                          <div id="appo-status" name="div-appo" onclick="contentChange(this)">
                              예약자 현황 / 관리
                          </div>
                      </li>
                      <li>
                          <div id="appo-modify" name="div-appo-modify" onclick="contentChange(this)">
                              예약정보 관리
                          </div>
                      </li>
                      <li>
                          <div id="notice" name="div-notice" onclick="contentChange(this)">
                              공지사항 설정
                          </div>
                      </li>
                      <li>
                          <div id="hosp-doctor" name="div-doctor" onclick="contentChange(this)">
                              의사정보 추가 및 수정
                          </div>
                      </li>
                      <li>
                          <div id="userInfo-change" name="div-userInfo" onclick="contentChange(this)">
                              관리자 회원정보 변경
                          </div>
                      </li>
                  </ul>
              </div>
          </aside>

          <div id="main-section">


              <%-- 의사 선택 영역--%>
<%--              <h1>의사 선택</h1>--%>
<%--              <div id="selectedDoctor" style="display: none"></div>--%>
<%--              <div class="select-option"></div>--%>
<%--              --%>

              <fieldset id="docChoose">
                  <legend><b>의사 선택</b></legend>
                  <div id="selectedDoctor" style="display: none"></div>
                  <div class="select-option"></div>
              </fieldset>

              <!-- 스케줄 생성 -->
              <div class="main-div" id="div-schedule">
                  <c:import url="hospitalAppointmentCreate.jsp"/>
              </div>

              <!-- 예약자 현황/관리 -->
              <div class="main-div" id="div-appo">
                  <c:import url="hospitalAppointmentManagement.jsp" />
              </div>

              <!-- 예약정보 관리 -->
              <div class="main-div" id="div-appo-modify">
                  <c:import url="hospitalAdminAppoModify.jsp" />
              </div>

              <!-- 의사정보 추가 및 수정 -->
              <div class="main-div" id="div-doctor">
                  <c:import url="adminDoctorInfo.jsp"></c:import>
              </div>

              <div class="main-div" id="div-userInfo">
                  <c:import url="adminUpdate.jsp"></c:import>
              </div>

          </div>

      </div>
  </section>
</div>
<script src="/script/admin-index.js"></script>
<script src="/script/hospital-appo-create.js"></script>
<script src="/script/hospital-admin-appo-modify.js"></script>
<script src="/script/hospital-appo-management.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

</body>
    </c:otherwise>
</c:choose>
<%--<c:import url="footer.jsp"></c:import>--%>
</html>
