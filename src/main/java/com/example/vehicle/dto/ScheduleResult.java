package com.example.vehicle.dto;
import java.util.List;
public class ScheduleResult {
private int depotId;
private int totalAvailableHours;
private int totalHoursUsed;
private int maxImpactScore;
public List<String> selectedTaskIds;
public ScheduleResult(int depotId, int totalAvailableHours,int totalHoursUsed,int maxImpactScore, List<String> selectedTaskIds) {
	this.depotId = depotId;
	this.totalAvailableHours = totalAvailableHours;
	this.totalHoursUsed = totalHoursUsed;
	this.maxImpactScore = maxImpactScore;
	this.selectedTaskIds = selectedTaskIds;
	
}
public int getDepotId() {return depotId;}
public int getTotalAvailableHours() { return totalAvailableHours;}
public int getTotalHoursUsed() {return totalHoursUsed;}
public int getMaxImpactScore() {return maxImpactScore;}
public List<String> getSelectedTaskIDs() { return selectedTaskIds;}
}
