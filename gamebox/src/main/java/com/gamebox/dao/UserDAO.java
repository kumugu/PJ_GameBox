package com.gamebox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gamebox.dto.UserDTO;
import com.gamebox.util.DBConnection;

public class UserDAO {

	// 회원 가입(일반) 
	public boolean registerUser(UserDTO user) {
	    String sql = "INSERT INTO Users (user_id, password, name, email, role) VALUES (user_seq.NEXTVAL, ?, ?, ?, 'USER')";
	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, user.getPassword());
	        pstmt.setString(2, user.getName());
	        pstmt.setString(3, user.getEmail());

	        int result = pstmt.executeUpdate();
	        return result > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    // 로그인(일반/관리자 분기) 
    public UserDTO loginUser(String email, String password) {
        String sql = "SELECT user_id, email, name, role FROM Users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserDTO user = new UserDTO();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    // 회원 추가(관리자 메뉴)
    public void addUser(UserDTO user) throws Exception {
        String sql = "INSERT INTO Users (user_id, name, email, password, role) VALUES (user_seq.NEXTVAL, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword()); 
            pstmt.setString(4, user.getRole());
            pstmt.executeUpdate();
        }
    }

    
    


    // 전체 회원 조회
    public List<UserDTO> getAllUsers() throws Exception {
        String sql = "SELECT * FROM Users";
        List<UserDTO> userList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }
        }

        return userList;
    }

    // 특정 회원 조회
    public UserDTO getUserById(int userId) throws Exception {
        String sql = "SELECT * FROM Users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    UserDTO user = new UserDTO();
                    user.setUserId(rs.getInt("user_id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }

        return null;
    }
    
    // 회원 수정
    public void updateUser(UserDTO user) throws Exception {
        String sql = "UPDATE Users SET name = ?, email = ?, role = ? WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getRole());
            pstmt.setInt(4, user.getUserId());
            pstmt.executeUpdate();
        }
    }

    // 회원 삭제
    public void deleteUser(int userId) throws Exception {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }
    
    
    
    

}
