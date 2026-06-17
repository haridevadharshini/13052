package com.example.vehicle.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public class VehicleResponse {
	private List<VehicleTask> vehicles;
	public List<VehicleTask> getVehicles(){
		return vehicles;
	}
	public void setVehicles(List<VehicleTask> vehicles) {
		this.vehicles = vehicles;
	}
	public static class VehicleTask{
		@JsonProperty("TaskID")
		private String taskId;
		@JsonProperty("Duration")
		private  int duration;
		@JsonProperty("Impact")
		private int impact;
		
		public String getTaskId() {
			return taskId;
		}
		public int getDuration() {
			return duration;
		}
		public int getImpact() {
			return impact;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;}
		public void setDuration(int duration) {
			this.duration = duration;}
		public void setImpact(int impact) {
			this.impact = impact;}
		
		}
	}
	
