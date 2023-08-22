package com.spring.kiddiecare.util.shift;

import com.spring.kiddiecare.domain.doctor.Doctor;
import com.spring.kiddiecare.domain.doctor.DoctorRepository;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimit;
import com.spring.kiddiecare.domain.timeSlotsLimit.TimeSlotsLimitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final DoctorRepository doctorRepository;
    private final TimeSlotsLimitRepository timeSlotsLimitRepository;

    // shiftController의 range와 맞춰줄것
    private final int range = 7;

    @Transactional
    public void updateShift(ShiftRequestDto[] shiftDtos){
        for(ShiftRequestDto shiftDto : shiftDtos){
            Optional<Shift> foundShift = shiftRepository.findByNo(shiftDto.getNo());
            if(foundShift.isEmpty()){continue;}
            Shift shift = foundShift.get();
            shift.update(shiftDto);
        }
    }


    @Transactional
    public void activeShift(ShiftRequestDto[] shiftDtos, long doctorNo){
        Optional<Doctor> foundDoctor = doctorRepository.findById(doctorNo);
        if(foundDoctor.isPresent()){
            Doctor doctor = foundDoctor.get();
            String ykiho = doctor.getYkiho();

            // active
            for(ShiftRequestDto shiftDto : shiftDtos){
                Optional<Shift> foundShift = shiftRepository.findByNo(shiftDto.getNo());
                if(foundShift.isEmpty()){continue;}
                Shift shift = foundShift.get();
                shift.setActive(true);
            }

            // timeslot 생성
            LocalDate now = LocalDate.now();
            for(int i=0; i<range; i++){
                LocalDate date = now.plusDays(i);
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                int index = dayOfWeek.getValue() - 1; // 월 : 1-1=0, 일 : 7-1=6

                ShiftRequestDto shiftDto = shiftDtos[index];
                Shift shift = new Shift(shiftDto);

                ArrayList<TimeSlotsLimit> timeSlotsLimits = getTimeslotsByshift(shift, date);

                timeSlotsLimitRepository.saveAll(timeSlotsLimits);
            }
        }



    }

    @Transactional
    public void deactiveShift(ShiftRequestDto[] shiftDtos){
        for(ShiftRequestDto shiftDto : shiftDtos){
            Optional<Shift> foundShift = shiftRepository.findByNo(shiftDto.getNo());
            if(foundShift.isEmpty()){continue;}
            Shift shift = foundShift.get();
            shift.setActive(false);
        }
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
}
