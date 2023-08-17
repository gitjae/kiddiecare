package com.spring.kiddiecare.domain.likeHospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeHospitalRepository extends JpaRepository<LikeHospital, Integer> {
    boolean existsByUserNoAndYkiho(int userNo, String ykiho);
    LikeHospital findByUserNoAndYkiho(int userNo, String ykiho);
}
