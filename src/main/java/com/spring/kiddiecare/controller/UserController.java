package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import com.spring.kiddiecare.domain.user.UserResponseDto;
import com.spring.kiddiecare.service.UserService;
import com.spring.kiddiecare.util.RandomUtil;
import com.spring.kiddiecare.util.SMSSender;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SMSSender smsSender;

    /**
     * 사용자 회원가입
     * @param userDto 회원가입 할 사용자 정보가 담긴 Dto
     * @return 회원가입 결과가 담긴 JSON -> MAP
     */
    @PostMapping("join")
    public Map join(@RequestBody UserRequestDto userDto){
        JSONObject jsonObject = new JSONObject();

        // 아이디 중복 확인
        Optional<User> dupl = userRepository.findUserById(userDto.getId());

        if(!dupl.isEmpty()){
            jsonObject.put("join", "dupl");
            return jsonObject.toMap();
        }

        User user = new User(userDto);
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        jsonObject.put("join", "success");

        return jsonObject.toMap();
    }

    /**
     * User 객체 대신 ResponseDto로 수정 필요
     * 사용자 정보 조회
     * @param id 사용자 아이디
     * @return
     */
    @GetMapping("user/{id}")
    public User getUser(@PathVariable String id){
        User user = null;
        Optional<User> foundUser = userRepository.findUserById(id);
        if(!foundUser.isEmpty()){
            user = foundUser.get();
        }
        return user;
    }

    /**
     * 사용자 정보를 업데이트 하는 컨트롤러
     * @param id 사용자 아이디
     * @param userDto 업데이트 할 사용자 정보가 담긴 Dto
     * @param request 세션 로그인 계정 확인용 WebRequest 객체
     * @return 업데이트 결과가 담긴 MAP
     */
    @PutMapping("user/{id}/update")
    public Map updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto, WebRequest request){
        JSONObject json = new JSONObject();
        // 수정할 사용자와 로그인한 사용자가 일치하는지 확인
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        if(!log.equals(id)){return json.put("update", "fail").toMap();}

        // 사용자 정보 수정 서비스
        boolean check = userService.updateUser(id, userDto);

        if(check){
            json.put("update","success");
            return json.toMap();
        }
        json.put("update","fail");
        return json.toMap();
    }

    /**
     * 사용자 계정 탈퇴 컨트롤러
     * @param id 탈퇴 할 사용자 아이디
     * @param request 세션 로그인 계정 확인용 WebRequest 객체
     * @param status 세션에서 로그아웃 처리를 위한 SessionStatus 객체
     * @return
     */
    @DeleteMapping("user/{id}/delete")
    public Map deleteUser(@PathVariable String id, WebRequest request, SessionStatus status){
        JSONObject json = new JSONObject();
        // 탈퇴할 사용자와 로그인 한 사용자가 일치하는지 확인
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        if(!log.equals(id)){return json.put("delete", "fail").toMap();}

        // 사용자 탈퇴 서비스
        boolean check = userService.deleteUser(id);

        if(check){
            json.put("delete","success");

            status.setComplete();

            request.removeAttribute("log", WebRequest.SCOPE_SESSION);

            return json.toMap();
        }
        json.put("delete","fail");
        return json.toMap();
    }

    /**
     * 회원가입용 전화번호 인증 코드 발송
     * @param number 코드 수신 번호
     * @param session 코드 저장을 위한 세션
     * @return 문자 발송 정보가 담긴 Map
     */
    @PostMapping("sendcode/join")
    public Map SmsSend(@RequestParam String number, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        // 전화번호 중복 확인
        Optional<User> foundUser = userRepository.findUserByPhone(number);
        if (foundUser.isPresent()){
            jsonObject.put("send","fail");
            jsonObject.put("dupl","true");
            return jsonObject.toMap();
        }

        //SMSSender smsSender = new SMSSender(); -> new 키워드로 직접 객체 생성시 @Value 어노테이션을 통해 값 주입 불가
        // 인증 코드 난수 생성
        RandomUtil randomUtil = new RandomUtil();
        String code = randomUtil.makeRandomCode();

        String message = "[우리동네소아과] 인증번호 [" + code + "]를 입력해주세요.";

        /*// 임시 문자 발송 제한
        session.setAttribute("code", code);
        session.setAttribute("time", LocalDateTime.now());
        System.out.println(code);
        System.out.println(LocalDateTime.now());
        jsonObject.put("send","success");
        jsonObject.put("dupl","false");
        jsonObject.put("code",code);*/

        // 문자발송 코드
        Map responseBody = null;
        try {
            responseBody = smsSender.sendSms(number, message);
            // 문자 발송 응답 결과 확인
            if(responseBody != null){
                String statusCode = (String) responseBody.get("statusCode");
                // 202 : 성공
                if(statusCode.equals("202")){
                    // 세선에 인증 코드 및 발송 시간 저장
                    session.setAttribute("code", code);
                    session.setAttribute("time", LocalDateTime.now());

                    jsonObject.put("send","success");
                    jsonObject.put("dupl","false");
                    jsonObject.put("code",code);
                    return jsonObject.toMap();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jsonObject.put("send","fail");
        return jsonObject.toMap();
    }

    /**
     * 비밀번호 찾기용 전화번호 인증 코드 발송
     * @param userDto 수신번호를 포함 한 찾을 사용자 계정 정보가 담긴 Dto
     * @param session 코드 저장을 위한 세션
     * @return
     */
    @PostMapping("sendcode/find")
    public Map SmsSendFind(@RequestBody UserRequestDto userDto, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        // 아이디, 이름, 전화번호 모두 일치하는 계정 확인
        String number = userDto.getPhone();
        String name = userDto.getName();
        String id = userDto.getId();
        Optional<User> foundUser = userRepository.findUserByIdAndNameAndPhone(id, name, number);
        if (foundUser.isPresent()){
            //SMSSender smsSender = new SMSSender();
            RandomUtil randomUtil = new RandomUtil();
            String code = randomUtil.makeRandomCode();

            String message = "[우리동네소아과] 인증번호 [" + code + "]를 입력해주세요.";

            /*// 임시 문자 발송 제한
            session.setAttribute("code", code);
            session.setAttribute("time", LocalDateTime.now());
            System.out.println(code);
            System.out.println(LocalDateTime.now());
            jsonObject.put("send","success");
            jsonObject.put("code",code);*/

            // 문자발송 코드
            Map responseBody = null;
            try {
                responseBody = smsSender.sendSms(number, message);
                if(responseBody != null){
                    String statusCode = (String) responseBody.get("statusCode");
                    if(statusCode.equals("202")){
                        session.setAttribute("code", code);
                        session.setAttribute("time", LocalDateTime.now());

                        jsonObject.put("send","success");
                        //jsonObject.put("dupl","false");
                        jsonObject.put("code",code);
                        return jsonObject.toMap();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            jsonObject.put("send","fail");
            return jsonObject.toMap();
        }

        jsonObject.put("send","fail");
        return jsonObject.toMap();
    }

    /**
     * 인증 코드 확인
     * @param code 사용자가 입력한 인증 코드 번호
     * @param session 문자 발송 시 저장한 인증 코드가 담긴 세션
     * @return 인증 코드 확인 결과가 담긴 Map
     */
    @GetMapping("verify")
    public Map verifyCode(@RequestParam(name = "code") String code, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        // 세션에 저장된 코드와 발송 시간 확인
        String sessionCode = (String) session.getAttribute("code");
        LocalDateTime time = (LocalDateTime) session.getAttribute("time");

        if(sessionCode != null && time != null){
            // 코드 유효 기간 (3분) 확인
            if(time.plusMinutes(3).isAfter(LocalDateTime.now())){
                // 코드 일치 확인
                if (sessionCode.equals(code)){
                    jsonObject.put("verify", "success");
                    return jsonObject.toMap();
                }
            }
        }

        jsonObject.put("verify", "fail");
        return jsonObject.toMap();
    }

    @GetMapping("/getNoById")
    public ResponseEntity<?> getNoById(@RequestParam String id) {
        Optional<User> user = userRepository.findUserById(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get().getNo());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("제공된 ID로 사용자를 찾을 수 없음");
        }
    }

    /**
     * 아이디 찾기
     * @param name 사용자 이름
     * @param phone 사용자 전화번호
     * @return 사용자 아이디가 포함된 Dto
     */
    @GetMapping("findid")
    public Map findId(@RequestParam String name, @RequestParam String phone){
        JSONObject jsonObject = new JSONObject();

        // 이름과 전화번호 모두 일치하는 계정 확인
        Optional<User> foundUser = userRepository.findUserByNameAndPhone(name, phone);

        if(foundUser.isPresent()){
            User user = foundUser.get();
            UserResponseDto userDto = new UserResponseDto();
            userDto.setId(user.getId());
            jsonObject.put("find","success");
            jsonObject.put("user",userDto);
            return jsonObject.toMap();
        }
        jsonObject.put("find","fail");
        return jsonObject.toMap();
    }


    /**
     * 비밀번호 찾기를 통한 비밀번호 변경
     * @param userDto 사용자 아이디와 변경할 비밀번호가 담긴 Dto
     * @return
     */
    @PostMapping("findpw")
    public Map findpassword(@RequestBody UserRequestDto userDto){
        JSONObject jsonObject = new JSONObject();

        // 변경할 비밀번호로 사용자 정보 업데이트
        boolean check = userService.updateUser(userDto.getId(), userDto);
        if (check){
            return jsonObject.put("find", "success").toMap();
        }
        return jsonObject.put("find", "fail").toMap();
    }

    @GetMapping("getUser")
    public User getUser(@RequestParam int no) {
        return userRepository.findUserByNo(no);
    }

    @GetMapping("/getUserNoBySession")
    public ResponseEntity<Integer> getUserNoBySession(HttpSession session) {
        String loggedInUserName = (String) session.getAttribute("log");

        if (loggedInUserName == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 로그인되지 않은 사용자
        }

        Integer userNo = userService.findNoById(loggedInUserName);
        if (userNo != null) {
            return new ResponseEntity<>(userNo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findPhone")
    public ResponseEntity<?> getUserPhone(@RequestParam String id) {
        Optional<User> userOptional = userRepository.findUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return ResponseEntity.ok(user.getPhone());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

