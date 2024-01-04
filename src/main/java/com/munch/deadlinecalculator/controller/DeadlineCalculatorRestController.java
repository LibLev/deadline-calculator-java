package com.munch.deadlinecalculator.controller;

import com.munch.deadlinecalculator.business.api.DeadlineCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@AllArgsConstructor
public class DeadlineCalculatorRestController {

    private final DeadlineCalculatorService deadlineCalculatorService;

    @GetMapping("/get-deadline")
    public LocalDateTime getDeadline(@RequestBody Map<String, String> data) throws Exception {
        System.out.println("DÁTUM" + LocalDateTime.of(2024, 1, 4, 11, 25));
        //start date time format is: yyyy-MM-ddTHH:mm
        LocalDateTime start = LocalDateTime.parse(data.get("start"));
        long time = Integer.parseInt(data.get("time"));
        return deadlineCalculatorService.calculateDeadline(start, time);
    }

}
