package com.hexaware.petpals.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hexaware.petpals.services.Donation;
import com.hexaware.petpals.util.DBConnUtil;

public class ItemDonation extends Donation {
	
    private String itemType;
    private Connection connection = null;

    public ItemDonation(String donorName, double amount, String itemType) throws SQLException {
        super(donorName, amount);
        this.itemType = itemType;
        connection = DBConnUtil.getConnection();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public void recordDonation() {
        String query = "INSERT INTO donations (DonorName, DonationItem, DonationAmount, DonationDate) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, donorName);
            preparedStatement.setString(2, itemType);
            preparedStatement.setDouble(3, amount);
            preparedStatement.executeUpdate();
            System.out.println("Item donation recorded: " + this);
        } catch (SQLException e) {
            System.out.println("Error recording item donation: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "ItemDonation [Donor Name=" + donorName + ", Amount=" + amount + ", Item Type=" + itemType + "]";
    }


}
