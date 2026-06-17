package com.example.vehicle.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DepotResponse {
	
 private List<Depot> depots;
 public List<Depot> getDepots() {
	 return depots;
 }
 public void setDepots(List<Depot> depots) {
	 this.depots = depots;
 }
	 public static class Depot{
		 @JsonProperty("ID")
		 private int id;
		 @JsonProperty("MechanicHours")
		 private int mechanicHours;
		 
		 public int getId() {
			 return id;
		 }
		 public void setId(int id) {
			 this.id = id;
		 }
		 public int getMechanicHours() {
			 return mechanicHours;
		 }
		 public void setMechanicHours(int mechanicHours) {
			 this.mechanicHours = mechanicHours;
		 }
	 }
 }
