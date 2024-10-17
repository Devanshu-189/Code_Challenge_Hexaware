package com.hexaware.petpals.entity;

import com.hexaware.petpals.exception.InvalidPetAgeException;

public class Dog extends Pet{
	
	private String dogBreed;
	
	public Dog(String DogBreed, String name, int age, String breed) throws InvalidPetAgeException {
		super(name, age, breed);
		this.dogBreed = DogBreed;
	}

	public String getDogBreed() {
		return dogBreed;
	}

	public void setDogBreed(String DogBreed) {
		dogBreed = DogBreed;
	}
	
}
