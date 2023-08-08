package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import com.spring.kiddiecare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

