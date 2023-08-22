package com.spring.kiddiecare.util.shift;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ShiftController {
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;
    private final DoctorRepository doctorRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;

    // ShiftService의 range와 맞춰줄것
    private final int range = 7;
    private final String[] korWeekDays = {"월","화","수","목","금","토","일"};

    @PostMapping("api/v1/shift")
    public void shiftGen(@RequestBody ShiftRequestDto[] shiftDtos){
        // shift DB 저장
        ArrayList<Shift> shifts = new ArrayList<>();
        for(int i=0; i<shiftDtos.length; i++){
            Shift shift = new Shift(shiftDtos[i]);
            shifts.add(shift);
        } // 밑에서 한번에 저장

        shiftRepository.saveAll(shifts);
    }

    @PutMapping("api/v1/shift")
    public void updateShift(@RequestBody ShiftRequestDto[] shiftDtos){
        shiftService.updateShift(shiftDtos);
    }

    @PutMapping("api/v1/shift/active")
    public void activeShift(@RequestBody ShiftRequestDto[] shiftDtos, @RequestParam long doctorNo){
        shiftService.activeShift(shiftDtos, doctorNo);
    }

    @PutMapping("api/v1/shift/deactive")
    public void deactiveShift(@RequestBody ShiftRequestDto[] shiftDtos){
        shiftService.deactiveShift(shiftDtos);
    }

    public ArrayList<TimeSlotsLimit> getTimeslotsByshift(Shift shift, LocalDate date){
        long doctorNo = shift.getDoctorNo();
        Optional<Doctor> foundD = doctorRepository.findById(doctorNo);
        if(foundD.isEmpty()){return null;}
        Doctor doctor = foundD.get();
        String ykiho = doctor.getYkiho();

        Time startTime = shift.getStartTime();
        Time endTime = shift.getEndTime();
        Time lunchStart = shift.getLunchStartTime();
        Time lunchEnd = shift.getLunchEndTime();
        Time dinnerStart = shift.getDinnerStartTime();
        Time dinnerEnd = shift.getDinnerEndTime();

        long lunchStartL = 0;
        long lunchEndL = 0;
        long dinnerStartL = 0;
        long dinnerEndL = 0;

        if(lunchStart != null && lunchEnd != null){
            lunchStartL = lunchStart.getTime();
            lunchEndL = lunchEnd.getTime();
        }

        if(dinnerStart != null && dinnerEnd != null){
            dinnerStartL = dinnerStart.getTime();
            dinnerEndL = dinnerEnd.getTime();
        }

        long step = 60 * 60 * 1000;
        ArrayList<TimeSlotsLimit> timeSlotsLimits = new ArrayList<>();
        for(long time = startTime.getTime();time < endTime.getTime(); time += step){
            if(time >= lunchStartL && time < lunchEndL) {continue;}
            if(time >= dinnerStartL && time < dinnerEndL) {continue;}
            Time slotTime = new Time(time);
            Date sqlDate = Date.valueOf(date);
            int max = shift.getMax();

            TimeSlotsLimit timeSlotsLimit = new TimeSlotsLimit();
            timeSlotsLimit.setDoctorNo(doctorNo);
            timeSlotsLimit.setWeekday(shift.getWeekDay());
            timeSlotsLimit.setDate(sqlDate);
            timeSlotsLimit.setTime(slotTime);
            timeSlotsLimit.setCount(0);
            timeSlotsLimit.setMax(max);
            timeSlotsLimit.setBlock(0);
            timeSlotsLimit.setEnable(max);
            timeSlotsLimit.setYkiho(ykiho);

            timeSlotsLimits.add(timeSlotsLimit);
        }
        return timeSlotsLimits;
    }

    //@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void scheduler(){
        LocalDate now = LocalDate.now();
        LocalDate date = now.plusDays(range);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int index = dayOfWeek.getValue() - 1;
        List<Shift> shifts = shiftRepository.findAllByWeekDayAndActive(korWeekDays[index], true);

        for(Shift shift : shifts){
            ArrayList<TimeSlotsLimit> timeSlotsLimits = getTimeslotsByshift(shift, date);
            timeSlotsLimitRepository.saveAll(timeSlotsLimits);
        }
    }
}
