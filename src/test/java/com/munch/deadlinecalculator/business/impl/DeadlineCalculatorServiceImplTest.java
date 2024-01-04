package com.munch.deadlinecalculator.business.impl;

import com.munch.deadlinecalculator.exception.ReportTimeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineCalculatorServiceImplTest {

    private final DeadlineCalculatorServiceImpl deadlineCalculatorService = new DeadlineCalculatorServiceImpl();

    @Test
    void deadlineTestForWeekday() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 4, 10, 30);
        long time = 6;

        LocalDateTime result = deadlineCalculatorService.calculateDeadline(start, time);

        LocalDateTime expected = LocalDateTime.of(2024, 1, 4, 16, 30);
        assertEquals(expected, result);
    }

    @Test
    void deadlineTestForWeekend() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 4, 10, 30);
        long time = 18;

        LocalDateTime result = deadlineCalculatorService.calculateDeadline(start, time);

        LocalDateTime expected = LocalDateTime.of(2024, 1, 8, 12, 30);
        assertEquals(expected, result);
    }

    @Test
    void deadlineTestNextDay() throws Exception {
        LocalDateTime start = LocalDateTime.of(2024, 1, 4, 11, 25);
        long time = 6;

        LocalDateTime result = deadlineCalculatorService.calculateDeadline(start, time);

        LocalDateTime expected = LocalDateTime.of(2024, 1, 5, 9, 25);
        assertEquals(expected, result);
    }

    @Test
    void reportOnWeekend() {
        LocalDateTime start = LocalDateTime.of(2024, 1, 6, 11, 25);
        long time = 6;

        ReportTimeException exception = assertThrows(ReportTimeException.class, () -> deadlineCalculatorService.calculateDeadline(start,time));

        String expectedMessage = "Errors can only be reported on working days between 9am and 5pm";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}