package com.spring.kiddiecare.domain.likeHospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeHospitalRepository extends JpaRepository<LikeHospital, Integer> {
    boolean existsByUserNoAndYkiho(int userNo, String ykiho);
    LikeHospital findByUserNoAndYkiho(int userNo, String ykiho);

    /**
     * 사용자, 병원이름, 시군구 코드로 찜한 병원 찾는 메소드
     * @param userNo 사용자 PK값
     * @param hospitalName 병원 이름
     * @param sgguCd 시군구 코드
     * @return 찜한 병원
     */
    LikeHospital findByUserNoAndHospitalNameAndSgguCd(int userNo, String hospitalName, String sgguCd);

    Optional<LikeHospital> findLikeHospitalByUserNoAndYkiho(int userNo, String ykiho);

    /**
     * 사용자, 병원이름, 시군구 코드로 찜한 병원 찾는 메소드
     * @param userNo 사용자 PK값
     * @param hospitalName 병원 이름
     * @param sgguCd 시군구 코드
     * @return 찜한 병원
     */
    Optional<LikeHospital> findLikeHospitalByUserNoAndHospitalNameAndSgguCd(int userNo, String hospitalName, String sgguCd);

    List<LikeHospital> findByUserNo(int userNo);

}
