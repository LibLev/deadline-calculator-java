package com.munch.deadlinecalculator.business.impl;

import com.munch.deadlinecalculator.business.api.DeadlineCalculatorService;
import com.munch.deadlinecalculator.exception.ReportTimeException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
public class DeadlineCalculatorServiceImpl implements DeadlineCalculatorService {

    private static final int START_OF_WORK = 9;
    private static final int END_OF_WORK = 17;
    private static final int WORKING_TIME = 8;

    @Override
    public LocalDateTime calculateDeadline(LocalDateTime start, long time) throws Exception {
        checkStartDate(start);

        LocalDateTime result = getTmpResult(start, time);

        //check if the result after work time
        boolean isAfterWorkingTime = result.getHour() >= END_OF_WORK && result.getMinute() > 0;

        return isAfterWorkingTime ? deadlineOnTheNextDay(result) : result;
    }

    private LocalDateTime deadlineOnTheNextDay(LocalDateTime localDateTime) {
        int minute = localDateTime.getMinute();
        int hour = localDateTime.getHour();
        int hourOfNextDay = hour - END_OF_WORK + START_OF_WORK;
        return localDateTime.toLocalDate().plusDays(1).atTime(hourOfNextDay, minute);
    }

    private LocalDateTime getTmpResult(LocalDateTime start, long time) {
        //calculate the number of days and hours
        long plusDays = time / WORKING_TIME;
        long plusHours = time % WORKING_TIME;

        //check if the result on weekend
        DayOfWeek dayOfWeek = start.plusDays(plusDays).getDayOfWeek();
        long plusWeekendDays = switch (dayOfWeek) {
            case SATURDAY -> 2;
            case SUNDAY -> 1;
            default -> 0;
        };

        return start.plusDays(plusDays + plusWeekendDays).plusHours(plusHours);
    }

    private void checkStartDate(LocalDateTime start) throws ReportTimeException {
        if (start.getDayOfWeek() == DayOfWeek.SATURDAY
                || start.getDayOfWeek() == DayOfWeek.SUNDAY
                || start.getHour() < START_OF_WORK
                || start.getHour() > END_OF_WORK) {
            throw new ReportTimeException("Errors can only be reported on working days between 9am and 5pm");
        }
    }
}
