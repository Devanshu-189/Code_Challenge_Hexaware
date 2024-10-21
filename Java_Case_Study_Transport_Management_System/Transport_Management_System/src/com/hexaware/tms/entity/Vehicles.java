package com.hexaware.tms.entity;

public class Vehicles {
	
	private int vehicleID; 
    private String model;  
    private double capacity; 
    private String type;   
    private String status; 
    
    
    public Vehicles() {}
    
    public Vehicles(String model, double capacity, String type, String status) {
        this.model = model;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
    }

   
    public Vehicles(int vehicleID, String model, double capacity, String type, String status) {
    	this.vehicleID = vehicleID;
        this.model = model;
        this.capacity = capacity;
        this.type = type;
        this.status = status;
    }

    
    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID=" + vehicleID +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
