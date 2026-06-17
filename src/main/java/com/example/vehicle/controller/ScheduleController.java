package com.example.vehicle.controller;
 import com.example.vehicle.dto.ScheduleResult;
 import com.example.vehicle.service.SchedulerService;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import java.util.List;
 
 @RestController
 @RequestMapping("/api/v1/scheduler")
 public  class ScheduleController {
 private final SchedulerService schedulerService;
 public ScheduleController(SchedulerService schedulerService) {
	 this.schedulerService = schedulerService;
 }
 @GetMapping("/optimize")
 public ResponseEntity<List<ScheduleResult>> getOptimalSchedules(
		 @RequestHeader("Authorization") String authToken){
	 List<ScheduleResult> optimizationResults = schedulerService.generateSchedules(authToken);
	 return ResponseEntity.ok(optimizationResults);
 }
}
