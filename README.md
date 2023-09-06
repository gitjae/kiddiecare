# kiddiecare
### 프로젝트 개요
  - 소아과 진료 대란으로 인한 진료 예약 오픈런 사회 문제 발생
  - 위치 기반 진료 가능 소아과 예약 서비스를 통한 사회 문제 완화 목표  
  - [서비스 소개](https://www.canva.com/design/DAFsau9GZBY/2M8eC4xSIYN6akEFfxHpSw/edit?utm_content=DAFsau9GZBY&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton) 

### 작업기간
- 2023.08.01 ~ 2023.08.25(3주)

### 테스크툴
- 우리동네소아과 Notion [link](https://sally-.notion.site/Index-7ee893e575af456e98a09b7bfae9ea0f?pvs=4)
- 회의록 [link](https://sally-.notion.site/5beff6a68f144e18bf806deda0353b0a?v=9e172e94f7654f0a9538f797ba60e429&pvs=4)

### 사이트
[kiddiecare 사이트 바로가기](https://kiddiecare.site/)

### 아키텍처
![image](/KiddiecareArchitecture.png)

### 기능 및 담당자
- 팀장 이규재✨[이메일](mailto:dlrbwo2022@gmail.com) ([GitHub](https://github.com/gitjae))
  * 사용자 CRUD 구현
      - 회원가입
      - 로그인
      - 회원탈퇴
      - 회원정보수정
        
  * 카카오 API 연동
      - 주소 검색
      - 로그인
      - 카카오맵
        
  * 공공데이터포털 API 연동
      - 병원 정보
        
  * 문자발송서비스 SENS API 연동
      - 회원가입시 휴대폰 인증 문자 발송
   
  * 마이페이지 구현
      - 예약정보 확인 및 수정
      - 자녀 CRUD
  
- 팀원 채희재✨[이메일](mailto:heejae0629@naver.com) ([GitHub](https://github.com/heejae101?tab=repositories))
  - WAS 아키텍처 구현
  
  - Docker 환경 설정
   - redis 환경 구축
   
  - 공공 데이터포털 API 연동
    - 여러 API에서 받아온 공공 데이터를 WAS에서 데이터 정제
    - redis 데이터 저장 후 캐시 기능 구현
    
  - 병원 관리자 기능 & 의사정보 CRUD
    - OPEN API로 구현한 병원 상세 주소 검색기능 구현
    - javax.mail의 SMTP프로토콜을 사용하여 이메일 인증 기능 구현 
    - Spring security의 BCrypt해쉬알고리즘으로 레인보우 테이블 대입을 방지
    - AWS S3 & ClouldFront를 이용한 정적 파일 관리
    
  - websocket 알림 기능
    - peer to peer로 실시간 알람 기능 구현
    - pub/sub 구조 구현 (현재 버전에서 제외)
    
  - swagger
    - REST API로 구현된 로직들을 문서화하여 한눈에 볼 수 있는 기능 구현(현재 버전에서 제외)
   
  - excel 파일 파싱 및 파일 파일 다운로드
    -  병원 정보 업로드 & 파싱 구현 (현재 버전에서 부분 구현)
    -  병원 예약정보 파일 파일 다운로드 (현재 버전에서 제외)
      
- 팀원 한희수✨[이메일](mailto:juntu09@gmail.com) ([GitHub](https://github.com/hee-duck))
  * 사용자 병원 예약 기능 구현
    - 병원 상세페이지
        - 카카오맵 API를 사용하여 해당 병원의 지도 표시
        - 공공데이터포털 API에서 추출한 병원 기본 정보(운영시간, 주차정보 등), local DB 의사정보 표기
        - 예약시 날짜 > 진료시간 > 담당의사 선택 순서에 따른 영역 활성화
        - 로그인 유무 판별 후 예약정보입력 버튼 활성화
    - 예약정보 입력 페이지
        - 상세페이지에서 선택한 병원, 날짜, 진료시간, 담당의사 값 readonly 표기
        - 필수 약관 필수 동의 처리
        - 로그인한 유저의 자녀 정보 활성화 및 선택 처리
        - 예약 완료 시 예약정보 DB 저장
    - 결제
        - iamport API 연동을 통한 카카오페이 결제 시스템 구현
      
  * 병원 찜(좋아요) 기능 구현
    - 병원 상세페이지
        - 좋아요 유무에 따라 지도 우측 상단 버튼 활성화
        - 버튼 클릭 액션에 따른 DB 저장 및 삭제 
    - 마이페이지 -> 찜한 병원
        - 찜한 병원 리스트 처리
        - 클릭 시 해당 병원 상세페이지 이동

  * 서비스 디자인(CSS) 총괄
    
- 팀원 이윤정✨[이메일](mailto:dldbswjd889@naver.com) ([GitHub](https://github.com/yunJeong3))
  * 관리자 예약 관리 페이지
    - 관리자 스케줄 관리 기능
      - 한 타임당 유저가 예약이 가능하게 병원 스케줄을 생성
      - 스케줄에 등록된 예약 정보 변경 가능
        
    - 관리자 유저 예약 관리 기능
      - 유저가 예약한 날짜/시간 변경 가능
      - 유저의 예약상태 변경 가능
     
    - web socket 알림 기능
      - peer to peer로 실시간 알람 기능 구현

