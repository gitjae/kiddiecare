package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalResponseDto;
import com.spring.kiddiecare.domain.likeHospital.LikeHospital;
import com.spring.kiddiecare.domain.likeHospital.LikeHospitalRepository;
import com.spring.kiddiecare.domain.user.User;
import com.spring.kiddiecare.domain.user.UserRepository;
import com.spring.kiddiecare.service.LikeHospitalService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeHospitalRepository likeHospitalRepository;
    private final UserRepository userRepository;
    private final LikeHospitalService likeHospitalService;

    @PostMapping("/api/like")
    public void likeHospital(HttpSession session, @RequestParam String hospitalName, @RequestParam String sgguCd) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);

        if(foundUser.isPresent()) {
            User user = foundUser.get();
            LikeHospital likeHospital = new LikeHospital();

            likeHospital.setHospitalName(hospitalName);
            likeHospital.setSgguCd(sgguCd);
            likeHospital.setUserNo(user.getNo());

            likeHospitalRepository.save(likeHospital);
        }
    }

    @DeleteMapping("/api/like")
    public void unlikeHospital(HttpSession session,@RequestParam String hospitalName, @RequestParam String sgguCd) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);
        if(foundUser.isPresent()) {
            User user = foundUser.get();
            LikeHospital existingLikeHospital = likeHospitalRepository.findByUserNoAndHospitalNameAndSgguCd(user.getNo(), hospitalName, sgguCd);
            likeHospitalRepository.delete(existingLikeHospital);
        }
    }

    @GetMapping("/api/like")
    public boolean isLiked(HttpSession session,@RequestParam String hospitalName, @RequestParam String sgguCd) {
        String id = (String) session.getAttribute("log");
        Optional<User> foundUser = userRepository.findUserById(id);
        boolean check = false;
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            // check = likeHospitalRepository.existsByUserNoAndYkiho(user.getNo(), ykiho);
            Optional<LikeHospital> found = likeHospitalRepository.findLikeHospitalByUserNoAndHospitalNameAndSgguCd(user.getNo(), hospitalName, sgguCd);
            if(found.isPresent()){
                check = true;
            }
        }
        return check;
    }

    @GetMapping("/api/likedHospitals")
    public Map getLikedHospitals(HttpSession session) {
        String userId = (String) session.getAttribute("log"); // userId를 세션에서 가져옴
        Optional<User> foundUser = userRepository.findUserById(userId); // userId로 User를 찾음

        if (!foundUser.isPresent()) {
            throw new RuntimeException("User not found.");
        }

        int userNo = foundUser.get().getNo();

        //List<Hospital> likedHospitals = likeHospitalService.getLikedHospitalsByUserNo(userNo);
        List<LikeHospital> likedHospitals = likeHospitalRepository.findByUserNo(userNo);
        /*return likedHospitals.stream()
                .map(hospital -> new HospitalResponseDto(hospital.getYkiho(), hospital.getHospitalName()))
                .collect(Collectors.toList());*/

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", likedHospitals);
        return jsonObject.toMap();
    }

}
