package DAO;

import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Util.ConnectionUtil;

public class AccountDAO {

    public Account login(Account acc) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, acc.getUsername());
            pStatement.setString(2, acc.getPassword());
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Account rsAcc = new Account(rs.getInt("account_id"),
                rs.getString("username"), 
                rs.getString("password"));
                return rsAcc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account insertAccount (Account acc) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pStatement.setString(1, acc.getUsername());
            pStatement.setString(2, acc.getPassword());
            pStatement.executeUpdate();
            ResultSet pkeyResultSet = pStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_acc_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_acc_id, acc.getUsername(), acc.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(String uName) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, uName);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Account acc = new Account(rs.getInt("account_id"),
                rs.getString("username"), 
                rs.getString("password"));
                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}