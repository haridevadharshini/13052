package com.example.vehicle.service;

import com.example.vehicle.dto.*;
import com.example.vehicle.dto.VehicleResponse.VehicleTask;
import com.example.vehicle.dto.VehicleResponse;
import com.example.vehicle.dto.DepotResponse.Depot;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final String DEPOT_API = "http://4.224.186.213/evaluation-service/depots";
    private final String VEHICLE_API = "http://4.224.186.213/evaluation-service/vehicles";

    public List<ScheduleResult> generateSchedules(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<DepotResponse> depotEntity = restTemplate.exchange(DEPOT_API, HttpMethod.GET, entity, DepotResponse.class);
        ResponseEntity<VehicleResponse> vehicleEntity = restTemplate.exchange(VEHICLE_API, HttpMethod.GET, entity, VehicleResponse.class);
        
        if (depotEntity.getBody() == null || vehicleEntity.getBody() == null) {
            return Collections.emptyList();
        }
        
        List<DepotResponse.Depot> depots = depotEntity.getBody().getDepots();
        List<VehicleTask> tasks = new ArrayList<>(vehicleEntity.getBody().getVehicles());
        List<ScheduleResult> results = new ArrayList<>();
        
        for (DepotResponse.Depot depot : depots) {
            ScheduleResult result = optimizeDepotSelection(depot, tasks);
            results.add(result);
            Set<String> assignedIds = new HashSet<>(result.getSelectedTaskIDs());
            tasks.removeIf(task -> assignedIds.contains(task.getTaskId()));
        }
        
        return results;
    }
    private ScheduleResult optimizeDepotSelection(DepotResponse.Depot depot, List<VehicleTask> tasks) {
        int W = depot.getMechanicHours();
        int n = tasks.size();
        int[][] dp = new int[n + 1][W + 1];
        
        for (int i = 1; i <= n; i++) {
            VehicleTask task = tasks.get(i - 1);
            int weight = task.getDuration();
            int value = task.getImpact();
            for (int w = 0; w <= W; w++) {
                if (weight <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - weight] + value);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        
        List<String> selectedTasks = new ArrayList<>();
        int resCapacity = W;
        int totalHoursUsed = 0;
        
        for (int i = n; i > 0 && resCapacity > 0; i--) {
            if (dp[i][resCapacity] != dp[i - 1][resCapacity]) {
                VehicleTask chosenTask = tasks.get(i - 1);
                selectedTasks.add(chosenTask.getTaskId());
                resCapacity -= chosenTask.getDuration();
                totalHoursUsed += chosenTask.getDuration();
            }
        }
        
        Collections.reverse(selectedTasks);
        
        return new ScheduleResult(
                depot.getId(),
                W,
                totalHoursUsed,
                dp[n][W],
                selectedTasks
        );
    }
}