package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.likeHospital.LikeHospital;
import com.spring.kiddiecare.domain.likeHospital.LikeHospitalRepository;
import com.spring.kiddiecare.domain.likeHospital.LikeHospitalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeHospitalRepository likeHospitalRepository;


    @PostMapping("/api/like")
    public void likeHospital(@RequestBody LikeHospitalRequestDto requestDto) {
        LikeHospital likeHospital = new LikeHospital(requestDto);
        likeHospitalRepository.save(likeHospital);
    }

    @DeleteMapping("/api/like/{userNo}/{ykiho}")
    public void unlikeHospital(@PathVariable int userNo, @PathVariable String ykiho) {
        LikeHospital existingLikeHospital = likeHospitalRepository.findByUserNoAndYkiho(userNo, ykiho);
        likeHospitalRepository.delete(existingLikeHospital);
    }

    @GetMapping("/api/like/existence")
    public boolean isLiked(@RequestParam int userNo, @RequestParam String ykiho) {
        return likeHospitalRepository.existsByUserNoAndYkiho(userNo, ykiho);
    }
}
