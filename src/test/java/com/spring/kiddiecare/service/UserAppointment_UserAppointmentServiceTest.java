package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.hospital.Hospital;
import com.spring.kiddiecare.domain.hospital.HospitalRepository;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentDto;
import com.spring.kiddiecare.domain.UserAppointment.UserAppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserAppointment_UserAppointmentServiceTest {

    /**
     * 테스트의 격리와 단위 테스트의 속도 향상을 위해 @InjectMocks, @Mock 사용
     * 서비스 계층의 메서드 테스트 시 실제 DB나 외부 시스템에 접근하는 로직은 테스트 대상이 아님
     * @Mock을 사용하여 가짜 리포지토리를 생성하고, @InjectMocks를 사용하여 서비스 객체에 주입
     * 실제 리포지토리를 사용하면 외부 환경(데이터베이스 서버의 상태)에 따라 테스트 결과가 달라질 수 있음
     * @Mock을 사용하면 이러한 외부 환경의 영향을 받지 않고 안정적으로 테스트를 수행할 수 있음
     */

    @InjectMocks        // 테스트 대상 클래스에 Mock 객체 주입
    private UserAppointment_Service userAppointmentService;

    @Mock               // Mock 객체 생성
    private UserAppointmentRepository userAppointmentRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @BeforeEach         // 각 테스트 메서드 실행 전 호출될 메서드
    public void setup() {
        MockitoAnnotations.initMocks(this);     // Mock 객체 초기화
    }


    // 예약 가능한 좌석이 있을 때 성공
    @Test
    public void user_booking_restSlots_success() {
        // Given : 테스트에 필요한 데이터와 상태 설정
        String ykiho = "abcdefg";
        Hospital mockHospital = new Hospital();
        mockHospital.setYkiho(ykiho);
        mockHospital.setHospitalMaxSlots(2);
        mockHospital.setReservesSlots(0);

        UserAppointmentDto requestDto = new UserAppointmentDto();
        requestDto.setYkiho(ykiho);
        requestDto.setUserId(1L);
        requestDto.setChildId(1L);

        when(hospitalRepository.findById(ykiho)).thenReturn(Optional.of(mockHospital));

        // When : 실제 테스트 대상 메서드 호출
        String result = userAppointmentService.book(requestDto);

        // Then : 결과 검증
        assertThat(result).isEqualTo("예약이 완료되었습니다.");
        verify(userAppointmentRepository, times(1)).save(any());
        verify(hospitalRepository, times(1)).save(any());
    }

    // 예약 가능 좌석 없을 때 실패 -> 예약 가능 수와 현재 예약 수를 같게 세팅
    @Test
    public void user_booking_restSlots_fail() {
        // Given
        String ykiho = "abcdefg";
        Hospital mockHospital = new Hospital();
        mockHospital.setYkiho(ykiho);
        mockHospital.setHospitalMaxSlots(2);
        mockHospital.setReservesSlots(2);

        UserAppointmentDto requestDto = new UserAppointmentDto();
        requestDto.setYkiho(ykiho);
        requestDto.setUserId(1L);
        requestDto.setChildId(1L);

        when(hospitalRepository.findById(ykiho)).thenReturn(Optional.of(mockHospital));     // 특정 메서드 호출 시 리턴값 설정

        // When
        String result = userAppointmentService.book(requestDto);

        // Then
        assertThat(result).isEqualTo("예약 가능한 좌석이 없습니다.");
        verify(userAppointmentRepository, times(0)).save(any());  // 예약 저장 x
        verify(hospitalRepository, times(0)).save(any());         // 병원 저장 x
    }
}
