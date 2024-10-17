package com.hexaware.petpals.entity;

import com.hexaware.petpals.exception.InvalidPetAgeException;

public class Pet {
	
	private String name;
	private int age;
	private String breed;
	
	public Pet() {}
	
	public Pet(String name, int age, String breed) throws InvalidPetAgeException{
		if (age <= 0) {
            throw new InvalidPetAgeException("Age must be a positive integer.");
        }
		this.setName(name);
		this.setAge(age);
		this.setBreed(breed);
	}

	public String getName() {
		return name;
	}

	public void setName(String petname) {
		name = petname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int petage) throws InvalidPetAgeException{
		if (petage <= 0) {
            throw new InvalidPetAgeException("Age must be a positive integer.");
        }
		age = petage;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String petbreed) {
		breed = petbreed;
	}
	
	public String toString() {
		return "Pet(Name: " + name + ", Age: " + age + ", Breed: " + breed + ")";
	}

}
