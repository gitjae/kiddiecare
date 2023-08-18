package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.likeHospital.LikeHospital;
import com.spring.kiddiecare.domain.likeHospital.LikeHospitalRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeHospitalRepository likeHospitalRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/like/{ykiho}")
    public void likeHospital(HttpSession session, @PathVariable String ykiho) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);

        if(foundUser.isPresent()) {
            User user = foundUser.get();
            LikeHospital likeHospital = new LikeHospital();

            likeHospital.setYkiho(ykiho);
            likeHospital.setUserNo(user.getNo());

            likeHospitalRepository.save(likeHospital);
        }
    }

    @DeleteMapping("/api/like/{userNo}/{ykiho}")
    public void unlikeHospital(HttpSession session,@PathVariable String ykiho) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);
        if(foundUser.isPresent()) {
            User user = foundUser.get();
            LikeHospital existingLikeHospital = likeHospitalRepository.findByUserNoAndYkiho(user.getNo(), ykiho);
            likeHospitalRepository.delete(existingLikeHospital);
        }
    }

    @GetMapping("/api/like/existence/{ykiho}")
    public boolean isLiked(HttpSession session, @PathVariable String ykiho) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            return likeHospitalRepository.existsByUserNoAndYkiho(user.getNo(), ykiho);
        }
        return false;
    }

}
