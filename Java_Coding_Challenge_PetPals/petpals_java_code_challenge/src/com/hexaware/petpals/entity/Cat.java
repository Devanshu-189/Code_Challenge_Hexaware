package com.hexaware.petpals.entity;

import com.hexaware.petpals.exception.InvalidPetAgeException;

public class Cat extends Pet{
	
	private String CatColor;
	
	public Cat(String CatColor, String name, int age, String breed) throws InvalidPetAgeException {
		super(name, age, breed);
		this.setCatColor(CatColor);
	}

	public String getCatColor() {
		return CatColor;
	}

	public void setCatColor(String catColor) {
		CatColor = catColor;
	}

}
