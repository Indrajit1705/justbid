package com.buysellplatform.dao;

import com.buysellplatform.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BidDAO {

    // Insert a bid into the database
    public boolean placeBid(int productId, int buyerId, double bidPrice) {
        String INSERT_BID_SQL = "INSERT INTO bids (product_id, buyer_id, bid_price, bid_time) VALUES (?, ?, ?, NOW())";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BID_SQL)) {

            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, buyerId);
            preparedStatement.setDouble(3, bidPrice);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("BidDAO: SQL exception - " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
