package com.hexaware.petpals.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hexaware.petpals.exception.InvalidPetAgeException;
import com.hexaware.petpals.util.DBConnUtil;

public class PetShelter {
	
    private List<Pet> availablePets;
    private Connection connection = null;

    public PetShelter() throws SQLException {
        availablePets = new ArrayList<>();
        connection = DBConnUtil.getConnection();
    }

    public void addPet(Pet pet) throws InvalidPetAgeException {
    	if (pet.getAge() <= 0) {
            throw new InvalidPetAgeException("Pet age must be a positive integer.");
        }
        String sql = "INSERT INTO pets (Name, Age, Breed, Type, AvailableForAdoption) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(4, pet.getName());
            preparedStatement.setInt(2, pet.getAge());
            preparedStatement.setString(3, pet.getBreed());
            preparedStatement.setString(1, pet instanceof Dog ? ((Dog) pet).getDogBreed() : (pet instanceof Cat ? ((Cat) pet).getCatColor() : null));
            preparedStatement.setBoolean(5, true);
            preparedStatement.executeUpdate();
            availablePets.add(pet); 
            System.out.println("Pet added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding pet: " + e.getMessage());
        }
    }

    public void removePet(Pet pet) {
        availablePets.remove(pet);
        System.out.println("Removed: " + pet);
    }

    public void listAvailablePets() {
        if (availablePets.isEmpty()) {
            System.out.println("No pets available for adoption.");
        } else {
            System.out.println("Available Pets:");
            for (Pet pet : availablePets) {
                System.out.println(pet);
            }
        }
    }
}
