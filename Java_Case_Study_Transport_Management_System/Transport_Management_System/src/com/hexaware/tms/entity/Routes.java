package com.hexaware.tms.entity;

public class Routes {
	
	private int routeID; 
    private String startDestination; 
    private String endDestination; 
    private double distance; 

    public Routes() {}

    public Routes(int routeID, String startDestination, String endDestination, double distance) {
        this.routeID = routeID;
        this.startDestination = startDestination;
        this.endDestination = endDestination;
        this.distance = distance;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public String getStartDestination() {
        return startDestination;
    }

    public void setStartDestination(String startDestination) {
        this.startDestination = startDestination;
    }

    public String getEndDestination() {
        return endDestination;
    }

    public void setEndDestination(String endDestination) {
        this.endDestination = endDestination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeID=" + routeID +
                ", startDestination='" + startDestination + '\'' +
                ", endDestination='" + endDestination + '\'' +
                ", distance=" + distance +
                '}';
    }

}
