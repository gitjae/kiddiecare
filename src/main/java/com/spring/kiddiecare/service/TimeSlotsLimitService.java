package com.spring.kiddiecare.service;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRequestDto;
import com.spring.kiddiecare.util.DateParsor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Time;
import java.util.Date;
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

    @Transactional
    public boolean setEnable(int timeSlotNo, int max, int block) {
        TimeSlotsLimit timeSlotsLimit = timeSlotsLimitRepository.findById(timeSlotNo)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + timeSlotNo));

        timeSlotsLimit.setMax(max);
        timeSlotsLimit.setBlock(block);
        timeSlotsLimit.setEnable(timeSlotsLimit.getMax() - (timeSlotsLimit.getCount() + timeSlotsLimit.getBlock()));

        int timeMax = timeSlotsLimit.getMax();
        int timeBlock = timeSlotsLimit.getBlock();
        int timeEnable = timeSlotsLimit.getEnable();


        // 만족해야 하는 조건
        // max >= enable
        if(timeMax >= timeEnable && timeEnable >= 0 && timeMax >= timeBlock) {
            timeSlotsLimitRepository.save(timeSlotsLimit);
            return true;
        }else {
            return false;
        }

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

                TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit(rowData);

                String ykiho = timeSlotsLimit.getYkiho();
                long doctorNo = timeSlotsLimit.getDoctorNo();
                Date getDate = timeSlotsLimit.getDate();
                Time getTime = timeSlotsLimit.getTime();

                boolean dupl = timeSlotsLimitRepository.existsByYkihoAndDoctorNoAndDateAndTime(ykiho, doctorNo, getDate, getTime);


                // 중복 안될 때 저장
                if(!dupl) {
                    // enable = max 같게 설정
                    timeSlotsLimit.setEnable(timeSlotsLimit.getMax());

                    timeSlotsLimitRepository.save(timeSlotsLimit);



                }
            }
        }
    }
}

