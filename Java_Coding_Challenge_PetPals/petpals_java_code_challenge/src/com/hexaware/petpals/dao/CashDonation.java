package com.hexaware.petpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import com.hexaware.petpals.services.Donation;
import com.hexaware.petpals.util.DBConnUtil;


public class CashDonation extends Donation {
	
    private LocalDate donationDate;
    private Connection connection = null;

    public CashDonation(String donorName, double amount, LocalDate donationDate) throws SQLException {
        super(donorName, amount);
        this.donationDate = donationDate;
        connection = DBConnUtil.getConnection();
    }


    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    @Override
    public void recordDonation() {
    	String query = "INSERT INTO donations (DonorName, DonationAmount, DonationDate) VALUES (?, ?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, donorName);
            preparedStatement.setDouble(2, amount);
            preparedStatement.executeUpdate();
            System.out.println("Cash donation recorded: " + this);
        } catch (SQLException e) {
            System.out.println("Error recording donation: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return "CashDonation [Donor Name=" + donorName + ", Amount=" + amount + ", Donation Date=" + donationDate + "]";
    }
}

