package com.hexaware.petpals.main;
import java.sql.SQLException;
import java.util.Scanner;

import com.hexaware.petpals.dao.AdoptionEvent;
import com.hexaware.petpals.dao.CashDonation;
import com.hexaware.petpals.entity.Cat;
import com.hexaware.petpals.entity.Dog;
import com.hexaware.petpals.entity.PetShelter;
import com.hexaware.petpals.exception.InsufficientFundsException;
import com.hexaware.petpals.exception.InvalidPetAgeException;
import com.hexaware.petpals.services.IAdoptable;

public class MainModule {
    public static void main(String[] args) throws SQLException {
    	PetShelter shelter = new PetShelter();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nPet Adoption Platform");
            System.out.println("1. Add Pet");
            System.out.println("2. List Available Pets");
            System.out.println("3. Record Cash Donation");
            System.out.println("4. Host Adoption Event");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter pet name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter pet age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter pet breed: ");
                    String breed = scanner.nextLine();
                    System.out.print("Enter type (dog/cat): ");
                    String type = scanner.nextLine();

                    if (type.equalsIgnoreCase("dog")) {
                        System.out.print("Enter dog breed: ");
                        String dogBreed = scanner.nextLine();
                        try {
                            Dog dog = new Dog(name, breed, age, dogBreed);
                            shelter.addPet(dog);
                        } catch (InvalidPetAgeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else if (type.equalsIgnoreCase("cat")) {
                        System.out.print("Enter cat color: ");
                        String catColor = scanner.nextLine();
                        try {
                            Cat cat = new Cat(name, breed, age, catColor);
                            shelter.addPet(cat);
                        } catch (InvalidPetAgeException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid pet type.");
                    }
                    break;

                case 2:
                    shelter.listAvailablePets();
                    break;

                case 3:
                    System.out.print("Enter donor name: ");
                    String donorName = scanner.nextLine();
                    System.out.print("Enter donation amount: ");
                    double cashAmount = scanner.nextDouble();

                    try {
                        if (cashAmount < 10) {
                            throw new InsufficientFundsException("Donation amount must be at least $10.");
                        }
                        CashDonation cashDonation = new CashDonation(donorName, cashAmount, null);
                        cashDonation.recordDonation();
                    } catch (InsufficientFundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 4:
                	AdoptionEvent adoptionEvent = new AdoptionEvent();
                    System.out.print("Enter participant name: ");
                    String participantName = scanner.nextLine();
                    adoptionEvent.registerParticipant(new IAdoptable() {
                        @Override
                        public void adopt() {
                            System.out.println(participantName + " has adopted a pet!");
                        }

                        @Override
                        public String toString() {
                            return "Participant Name: " + participantName;
                        }
                    });

                    adoptionEvent.hostEvent();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}

                       
