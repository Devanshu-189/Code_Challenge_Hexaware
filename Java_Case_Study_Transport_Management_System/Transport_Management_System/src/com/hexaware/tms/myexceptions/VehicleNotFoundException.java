package com.hexaware.tms.myexceptions;

public class VehicleNotFoundException extends Exception{
	
	public VehicleNotFoundException(String str) {
		super(str);
	}
	
	public VehicleNotFoundException() {
		System.out.println("Vehicle not found !");
	}
}
