package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TimeSlotsLimitService {
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;

    // 예약정보 수정 시 기존 시간의 time_slots_limit 테이블의 enable은 줄어들고,
    // 변경한 시간의 enable은 감소
    @Transactional
    public void plusEnable(int timeSlotNo) {
        // time_slots_limit -> no -> enable +1, count -1
        TimeSlotsLimit timeSlotsLimit = timeSlotsLimitRepository.findById(timeSlotNo)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + timeSlotNo));
        int newCount = timeSlotsLimit.getCount() -1;
        timeSlotsLimit.setCount(newCount);
        timeSlotsLimit.setEnable(timeSlotsLimit.getMax() - (newCount + timeSlotsLimit.getBlock()));

        timeSlotsLimitRepository.save(timeSlotsLimit);
    }

    @Transactional
    public void minusEnable(int timeSlotNo) {
        // time_slots_limit -> no -> enable -1, count +1
        TimeSlotsLimit timeSlotsLimit = timeSlotsLimitRepository.findById(timeSlotNo)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + timeSlotNo));
        int newCount = timeSlotsLimit.getCount() +1;
        timeSlotsLimit.setCount(newCount);
        timeSlotsLimit.setEnable(timeSlotsLimit.getMax() - (newCount + timeSlotsLimit.getBlock()));

        timeSlotsLimitRepository.save(timeSlotsLimit);
    }

//    @Transactional
//    public void blockAppo(int timeSlotNo) {
//
//    }

    // timeslot create
    @Transactional
    public void timeSlotsCreate(Map<String, List<TimeSlotsLimitRequestDto>> data){
        for (String key : data.keySet()) {
            List<TimeSlotsLimitRequestDto> dataList = data.get(key);
            for (TimeSlotsLimitRequestDto rowData : dataList) {
                String date = rowData.getDate();
                String weekday = rowData.getWeekday();
                int time = rowData.getTime();
                int max = rowData.getMax();
                // max랑 동일하게
                int enable = rowData.getMax();
                rowData.setEnable(enable);
                // 임시
//                rowData.setDoctorNo(2);
//                rowData.setYkiho("JDQ4MTYyMiM1MSMkMSMkMCMkODkkMzgxMzUxIzExIyQxIyQzIyQ3OSQyNjE4MzIjNDEjJDEjJDgjJDgz");

                // 잘 들어와졌는지 테스트
//                System.out.println("date " + date);
//                System.out.println("weekday " + weekday);
//                System.out.println("time " + time);
//                System.out.println("max " + max);
//                System.out.println("enable " + enable);

                TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(rowData);
                System.out.println("체크:" +timeSlotsLimit);
//                timeSlotsLimitRepository.findTimeSlotsLimitsByTimeSlotsLimit(timeSlotsLimit);

//                timeSlotsLimitRepository.save(timeSlotsLimit);
            }
        }
    }
}

