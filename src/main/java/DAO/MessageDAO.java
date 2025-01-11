package DAO;

import Model.Message;
import Model.Account;
import java.sql.*;
import Util.ConnectionUtil;
import java.util.List;
import java.util.ArrayList;


public class MessageDAO {
    
    public Message insertMessage(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, msg.getPosted_by());
            pStatement.setString(2, msg.getMessage_text());
            pStatement.setLong(3, msg.getTime_posted_epoch());
            pStatement.executeUpdate();
            ResultSet pkeyResultSet = pStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_msg_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_msg_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByPostedId(Message msg) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, msg.getPosted_by());
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

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                messages.add(msg);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMsgFromId(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                return msg;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessage(int id) {
        Connection connection = ConnectionUtil.getConnection();
        Message msgToDelete = null;
        try {
            msgToDelete = getMsgFromId(id);
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, id);
            pStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgToDelete;
    }

    public Message updateMessage(Message msg, int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id=?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, msg.getMessage_text());
            pStatement.setInt(2, id);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return getMsgFromId(id);
    }

    public List<Message> getMsgsByAccount(int id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement pStatement = connection.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"), 
                rs.getLong("time_posted_epoch"));
                messages.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
