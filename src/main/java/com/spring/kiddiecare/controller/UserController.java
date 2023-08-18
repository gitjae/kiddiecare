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

    @PostMapping("join")
    public Map join(@RequestBody UserRequestDto userDto){
        JSONObject jsonObject = new JSONObject();

        //Optional<User> dupl = userRepository.findUserById(userDto.getId());
        Optional<User> dupl = userRepository.findUserById(userDto.getId());

        if(!dupl.isEmpty()){
            jsonObject.put("join", "dupl");
            return jsonObject.toMap();
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto);
        user.setPassword(encodedPassword);

        userRepository.save(user);

        jsonObject.put("join", "success");

        return jsonObject.toMap();
    }

    @GetMapping("user/{id}")
    public User getUser(@PathVariable String id){
        User user = null;
        Optional<User> foundUser = userRepository.findUserById(id);
        if(!foundUser.isEmpty()){
            user = foundUser.get();
        }
        return user;
    }

    @PutMapping("user/{id}/update")
    public Map updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto, WebRequest request){
        JSONObject json = new JSONObject();
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        if(!log.equals(id)){return json.put("update", "fail").toMap();}

        boolean check = userService.updateUser(id, userDto);

        if(check){
            json.put("update","success");
            return json.toMap();
        }
        json.put("update","fail");
        return json.toMap();
    }

    @DeleteMapping("user/{id}/delete")
    public Map deleteUser(@PathVariable String id, WebRequest request, SessionStatus status){
        JSONObject json = new JSONObject();
        String log = (String) request.getAttribute("log", WebRequest.SCOPE_SESSION);

        if(!log.equals(id)){return json.put("delete", "fail").toMap();}

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

    @PostMapping("sendcode/join")
    public Map SmsSend(@RequestParam String number, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        int phone = Integer.parseInt(number);
        Optional<User> foundUser = userRepository.findUserByPhone(phone);
        if (foundUser.isPresent()){
            jsonObject.put("send","fail");
            jsonObject.put("dupl","true");
            return jsonObject.toMap();
        }

        SMSSender smsSender = new SMSSender();
        RandomUtil randomUtil = new RandomUtil();
        String code = randomUtil.makeRandomCode();

        String message = "[우리동네소아과] 인증번호 [" + code + "]를 입력해주세요.";

        // 임시 문자 발송 제한
        session.setAttribute("code", code);
        session.setAttribute("time", LocalDateTime.now());
        System.out.println(code);
        System.out.println(LocalDateTime.now());
        jsonObject.put("send","success");
        jsonObject.put("dupl","false");
        jsonObject.put("code",code);

        // 문자발송 코드
        /*Map responseBody = null;
        try {
            responseBody = smsSender.sendSms(number, message);
            if(responseBody != null){
                String statusCode = (String) responseBody.get("statusCode");
                if(statusCode.equals("202")){
                    session.setAttribute("code", code);
                    session.setAttribute("time", LocalDateTime.now());
                    System.out.println(LocalDateTime.now());
                    jsonObject.put("send","success");
                    jsonObject.put("dupl","false");
                    jsonObject.put("code",code);
                    return jsonObject.toMap();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        jsonObject.put("send","fail");*/
        return jsonObject.toMap();
    }

    @PostMapping("sendcode/find")
    public Map SmsSendFind(@RequestBody UserRequestDto userDto, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        int phone = userDto.getPhone();
        String name = userDto.getName();
        String id = userDto.getId();
        Optional<User> foundUser = userRepository.findUserByIdAndNameAndPhone(id, name, phone);
        if (foundUser.isPresent()){
            SMSSender smsSender = new SMSSender();
            RandomUtil randomUtil = new RandomUtil();
            String code = randomUtil.makeRandomCode();

            String message = "[우리동네소아과] 인증번호 [" + code + "]를 입력해주세요.";

            // 임시 문자 발송 제한
            session.setAttribute("code", code);
            session.setAttribute("time", LocalDateTime.now());
            System.out.println(code);
            System.out.println(LocalDateTime.now());
            jsonObject.put("send","success");
            jsonObject.put("code",code);

            // 문자발송 코드
            /*Map responseBody = null;
            try {
                responseBody = smsSender.sendSms(number, message);
                if(responseBody != null){
                    String statusCode = (String) responseBody.get("statusCode");
                    if(statusCode.equals("202")){
                        session.setAttribute("code", code);
                        session.setAttribute("time", LocalDateTime.now());
                        System.out.println(LocalDateTime.now());
                        jsonObject.put("send","success");
                        jsonObject.put("dupl","false");
                        jsonObject.put("code",code);
                        return jsonObject.toMap();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            jsonObject.put("send","fail");*/
            return jsonObject.toMap();
        }

        jsonObject.put("send","fail");
        return jsonObject.toMap();
    }

    @GetMapping("verify")
    public Map verifyCode(@RequestParam(name = "code") String code, HttpSession session){
        JSONObject jsonObject = new JSONObject();

        String sessionCode = (String) session.getAttribute("code");
        LocalDateTime time = (LocalDateTime) session.getAttribute("time");

        System.out.println("scode"+sessionCode);
        System.out.println("code"+code);
        System.out.println("time"+time);

        if(sessionCode != null && time != null){
            if(time.plusMinutes(3).isAfter(LocalDateTime.now())){
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

    @GetMapping("findid")
    public Map findId(@RequestParam String name, @RequestParam int phone){
        JSONObject jsonObject = new JSONObject();
        System.out.println("name:"+name+"/phone:"+phone);
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

    @PostMapping("findpw")
    public Map findPw(@RequestBody UserRequestDto userDto){
        JSONObject jsonObject = new JSONObject();

        String id = userDto.getId();
        String name = userDto.getName();
        int phone = userDto.getPhone();

        Optional<User> foundUser = userRepository.findUserByIdAndNameAndPhone(id, name, phone);

        if(foundUser.isPresent()){
            User user = foundUser.get();
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setPassword(user.getPassword());

            jsonObject.put("find", "success");
            jsonObject.put("user", userResponseDto);
            return jsonObject.toMap();
        }
        jsonObject.put("find", "fail");
        return jsonObject.toMap();
    }

    @GetMapping("getUser")
    public User getUser(@RequestParam int no) {
        return userRepository.findUserByNo(no);
    }

    @GetMapping("/userno")      // 수정 필요
    public ResponseEntity<User> getUserNo(@RequestParam String name) {
        User user = userService.findByUserName(name);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

