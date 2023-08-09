package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import com.spring.kiddiecare.domain.user.UserResponseDto;
import com.spring.kiddiecare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("join")
    public Map join(@RequestBody UserRequestDto userDto){
        JSONObject jsonObject = new JSONObject();

        //Optional<User> dupl = userRepository.findUserById(userDto.getId());
        Optional<User> dupl = userRepository.findUserById(userDto.getId());

        if(!dupl.isEmpty()){
            jsonObject.put("join", "dupl");
            return jsonObject.toMap();
        }

        User user = new User(userDto);

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
    public Map updateUser(@PathVariable String id, @RequestBody UserRequestDto userDto){
        JSONObject json = new JSONObject();

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
}

