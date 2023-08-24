<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Member profile</title>
    <link rel="shortcut icon" href="/image/favicon.ico">
    <link href="/css/common.css" rel="stylesheet">

    <style>
        .profile {
            float: left;
            width: 390px;
            margin: 0 1%;
            border: 1px solid #bebebe;
            border-radius: 10px;
            margin-bottom: 20px;
            transition: transform 0.3s ease;
            padding: 30px;
            box-sizing: border-box;
        }

        .profile:hover {
            transform: translateY(-5px);
        }

        .profile img {
            display: block;
            width: 100%;
            border-radius: 10px;
        }

        .profile ul {
            padding: 15px;
            background-color: #f0f0f0;
            list-style: none;
            text-align: left;
            margin-top: 20px;
            border-radius: 10px;
        }

        .profile li {
            margin-bottom: 10px;
            height: 30px;
            line-height: 30px;
        }

        .profile li:last-child {
            margin-bottom: 0;
        }

        .profile a {
            color: black;
        }

        img {
            width: 100%;
            height: 400px;
        }

        .img {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column; /* 이 부분을 추가합니다. */
            flex-wrap: wrap; /* 만약 화면 너비가 부족할 경우 줄바꿈을 하도록 설정합니다. */
            gap: 40px;
        }

        .profile-row {
            display: flex;
            flex-direction: row;
            gap: 40px;  /* 가로 간격 */
        }

        strong {
            font-size: 1.3rem;
            color: #000069;
        }

        div.info-row > img {
            width: 30px;
            height: 30px;
            margin-right: 10px;
        }

        .info-row {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .info-row:last-child {
            margin-bottom: 0;
        }

        .info-row img {
            width: 30px;
            height: 30px;
            margin-right: 10px;
        }

        .info-row a {
            color: black;
        }

        .info-row a:hover {
            color: #3E85EF;
        }
    </style>
</head>
<c:import url="header.jsp"/>
<body>
<div class="container">
    <section>
        <div class="img">
            <div class="profile-row">
                <div class="profile-box">
                    <div class="profile">
                        <img src="/image/gyujae.jpeg" alt="My Image">
                        <ul>
                            <li><strong>이규재(Gyujae Lee)</strong></li>
                            <div class="info-row">
                                <img src="/image/Email.png" alt="My Image">
                                <span>dlrbwo2022@gmail.com</span>
                            </div>
                            <div class="info-row">
                                <img src="/image/GitHub.png" alt="My Image">
                                <a href="https://github.com/gitjae"
                                   target="_blank">https://github.com/gitjae</a>
                            </div>
                            <div class="info-row">
                                <img src="/image/Phone.png" alt="My Image">
                                <span>010-8700-8416</span>
                            </div>
                        </ul>
                    </div>
                </div>

                <div class="profile-box">
                    <div class="profile">
                        <img src="/image/heejae.jpeg" alt="My Image">
                        <ul>
                            <li><strong>채희재(Heejae Chae)</strong></li>
                            <div class="info-row">
                                <img src="/image/Email.png" alt="My Image">
                                <span>heejae0629@naver.com</span>
                            </div>
                            <div class="info-row">
                                <img src="/image/GitHub.png" alt="My Image">
                                <a href="https://github.com/heejae101"
                                   target="_blank">https://github.com/heejae101</a>
                            </div>
                            <div class="info-row">
                                <img src="/image/Phone.png" alt="My Image">
                                <span>010-9451-8124</span>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="profile-row">
                <div class="profile-box">
                    <div class="profile">
                        <img src="/image/yunjeong.jpeg" alt="My Image">
                        <ul>
                            <li><strong>이윤정(Yunjeong Lee)</strong></li>
                            <div class="info-row">
                                <img src="/image/Email.png" alt="My Image">
                                <span>dldbswjd7879@gmail.com</span>
                            </div>
                            <div class="info-row">
                                <img src="/image/GitHub.png" alt="My Image">
                                <a href="https://github.com/yunJeong3"
                                   target="_blank">https://github.com/yunJeong3</a>
                            </div>
                            <div class="info-row">
                                <img src="/image/Phone.png" alt="My Image">
                                <span>010-2931-1424</span>
                            </div>
                        </ul>
                    </div>
                </div>

                <div class="profile-box">
                    <div class="profile">
                        <img src="/image/heesoo.jpeg" alt="My Image">
                        <ul>
                            <li><strong>한희수(Heesoo Han)</strong></li>
                            <div class="info-row">
                                <img src="/image/Email.png" alt="My Image">
                                <span>juntu09@gmail.com</span>
                            </div>
                            <div class="info-row">
                                <img src="/image/GitHub.png" alt="My Image">
                                <a href="https://github.com/hee-duck"
                                   target="_blank">https://github.com/hee-duck</a>
                            </div>
                            <div class="info-row">
                                <img src="/image/Phone.png" alt="My Image">
                                <span>010-7220-8935</span>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </section>
</div>
</body>
<c:import url="footer.jsp"/>
</html>
