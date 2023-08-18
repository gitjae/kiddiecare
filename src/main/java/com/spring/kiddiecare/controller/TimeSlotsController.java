package com.spring.kiddiecare.controller;

import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotSum;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import com.spring.kiddiecare.util.DateParsor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSInput;

import java.sql.Time;
import java.util.*;

@RestController
@SessionAttributes({"log"})
public class TimeSlotsController {
    @Autowired
    private TimeSlotsLimitRepository timeSlotsLimitRepository;

    // 상세페이지에서 타임슬롯 가져오기용
    @GetMapping("/timeSlotsDateGetByYkiho")
    public Map timeSlotsDateGetByYkiho(@RequestParam String ykiho, @RequestParam Date date){
        JSONObject jsonObject = new JSONObject();
        List<TimeSlotsLimit> slots = timeSlotsLimitRepository.findTimeSlotsLimitByYkihoAndDate(ykiho, date);

        ArrayList<TimeSlotSum> timeSlotSums = new ArrayList<>();
        ArrayList<Time> dupl = new ArrayList<>();
        for(TimeSlotsLimit slot1 : slots){
            if(dupl.contains(slot1.getTime())){
                continue;
            }
            TimeSlotSum sum = new TimeSlotSum(slot1);
            //sum.add(slot1);
            for(TimeSlotsLimit slot2 : slots){
                if(slot1.getTime().equals(slot2.getTime()) && slot1.getDoctorNo() != slot2.getDoctorNo()){
                    sum.add(slot2);
                    dupl.add(slot2.getTime());
                }
            }
            timeSlotSums.add(sum);
        }
        // 원래대로 돌리려면 timeSlotSums 대신 slots 넣기
        jsonObject.put("slots", timeSlotSums);
        return jsonObject.toMap();
    }

    // 예약 후 타임슬롯 count + 1
    @PostMapping("/updateCount")
    public ResponseEntity<?> updateCount(@RequestBody Map<String, Integer> request) {
        try {
            Integer slotNo = request.get("slotNo");
            Optional<TimeSlotsLimit> timeSlot = timeSlotsLimitRepository.findById(slotNo);

            if (timeSlot.isPresent()) {
                TimeSlotsLimit existingSlot = timeSlot.get();
                existingSlot.setCount(existingSlot.getCount() + 1);
                timeSlotsLimitRepository.save(existingSlot);

                return new ResponseEntity<>(Collections.singletonMap("success", true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("success", false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("success", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTimeSlots")
    public List<TimeSlotsLimit> getTimeSlots(@RequestParam String ykiho, String date, Long doctorNo) {

        Date parseDate = DateParsor.parse(date);
        return timeSlotsLimitRepository.findTimeSlotsLimitByYkihoAndDateAndDoctorNo(ykiho, parseDate, doctorNo);

    }

    @GetMapping("/getTimeSlotsForEnable")
    public List<TimeSlotsLimit> getTimeSlotsByEnable(@RequestParam String ykiho, String date, Long doctorNo) {
        Date parseDate = DateParsor.parse(date);
        return timeSlotsLimitRepository.findByYkihoAndDateAndDoctorNoAndEnableGreaterThanEqual(ykiho, parseDate, doctorNo,1);
    }

}

