package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.domain.user.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean updateUser(String id, UserRequestDto userDto){
        boolean check = false;

        Optional<User> foundUser = userRepository.findUserById(id);

        if(!foundUser.isEmpty()){
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(encodedPassword);
            User user = foundUser.get();
            user.update(userDto);

            check = true;
        }

        return check;
    }

    @Transactional
    public boolean deleteUser(String id){
        boolean check = false;

        Optional<User> foundUser = userRepository.findUserById(id);

        if(!foundUser.isEmpty()){
            User user = foundUser.get();
            user.setValid(false);

            check = true;
        }

        return check;
    }

    // 아이디로 유저 이름 찾기 -> 병원 찜
    public Integer findNoById(String id) {
        Optional<User> user = userRepository.findUserById(id);
        return user.map(User::getNo).orElse(null);
    }

}
