package com.munch.deadlinecalculator.business.api;

import java.time.LocalDateTime;

public interface DeadlineCalculatorService {
    LocalDateTime calculateDeadline(LocalDateTime start, long time) throws Exception;
}
