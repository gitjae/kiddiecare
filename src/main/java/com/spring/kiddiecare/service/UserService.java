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

    /**
     * 사용자 정보 수정 서비스
     * @param id 사용자 아이디
     * @param userDto 업데이트 할 사용자 정보를 담고있는 Dto 객체
     * @return 업데이트 성공 여부
     */
    @Transactional
    public boolean updateUser(String id, UserRequestDto userDto){
        boolean check = false;

        Optional<User> foundUser = userRepository.findUserById(id);

        if(!foundUser.isEmpty()){
            if(userDto.getPassword() != null){
                String encodedPassword = passwordEncoder.encode(userDto.getPassword());
                userDto.setPassword(encodedPassword);
            }
            User user = foundUser.get();
            user.update(userDto);

            check = true;
        }

        return check;
    }

    // 계정 탈퇴 시 정보 삭제 대신 비활성화
    @Transactional
    public boolean deactiveUser(String id){
        boolean check = false;

        Optional<User> foundUser = userRepository.findUserById(id);

        if(!foundUser.isEmpty()){
            User user = foundUser.get();
            user.setValid(false);

            check = true;
        }

        return check;
    }

    /**
     * 사용자 탈퇴 서비스
     * @param id 탈퇴할 사용자 아이디
     * @return 탈퇴 성공 여부
     */
    @Transactional
    public boolean deleteUser(String id){
        boolean check = false;

        Optional<User> foundUser = userRepository.findUserById(id);

        if(!foundUser.isEmpty()){
            User user = foundUser.get();
            userRepository.deleteById(user.getNo());

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
