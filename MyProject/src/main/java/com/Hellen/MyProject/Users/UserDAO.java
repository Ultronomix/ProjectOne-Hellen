package com.Hellen.MyProject.Users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.Hellen.MyProject.Common.ConnectionFactory;
import com.Hellen.MyProject.Exceptions.DataSourceException;



public class UserDAO {
	
    private String baseSelect = "select au.user_id, au.username, au.email, au.password, au.given_name, au.surname, au.is_active, au.role_id, ur.role " +
                                "from ers_users au " +
		                        "join user_roles ur " +
                                "on au.role_id = ur.role_id ";

   public List<User> getAllUsers() {
	   
	 List<User> allUsers = new ArrayList<>();
	 
	 try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		 
		 Statement state = conn.createStatement();
		 ResultSet rs = state.executeQuery(baseSelect);
		 allUsers = mapResultSet(rs);
		 
	 } catch (SQLException e) {
		 System.err.println("Something went wrong communicating with the database");
		 e.printStackTrace();
	 }
	  return allUsers; 
	   
   }
   public Optional<User> findUserByUsernameAndPassword(String username, String password) {
	  
	   String sql = baseSelect + "where au.username = ? and au.password = ?";
	   
	   try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		   PreparedStatement psmt = conn.prepareStatement(sql);
		   psmt.setString(1, username);
		   psmt.setString(2, password);
		   ResultSet rs = psmt.executeQuery();
		   return mapResultSet(rs).stream().findFirst();
		   
	   } catch (SQLException e) {
		   throw new DataSourceException(e);
	   }
   }
   public String save(User user) {
	   String sql = "insert into ers_users(user_id, username, email, password, given_name, surname,is_active, role, role_id) " +
                    "values (?, ?, ?, ?, ?, '')";
	   try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		   PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"id"});
		   pstmt.setString(1, user.getUsername());
		   pstmt.setString(2, user.getEmail());
		   pstmt.setString(3, user.getPassword());
		   pstmt.setString(4, user.getGivenName());
		   pstmt.setString(5, user.getSurname());
		   pstmt.setString(6, user.getIsActive());
		   pstmt.setString(7, user.getRole().getId());
		   
		   pstmt.executeUpdate();
		   
		   ResultSet rs = pstmt.getGeneratedKeys();
		   rs.next();
		   user.setUserId(rs.getString("id"));
		   
	   }catch (SQLException e) {
		   log("error", e.getMessage());
		   
	   }
	   log("info", "Successfully persisted new user with id: " + user.getUserId());
	   return user.getUserId();
   }
   
   private List<User> mapResultSet(ResultSet rs) throws SQLException {
	   List<User> users = new ArrayList();
	   while (rs.next()) {
		   User user = new User();
		   user.setUserId(rs.getString("user_id"));
		   user.setUsername(rs.getString("username"));
		   user.setEmail(rs.getString("email"));
		   user.setPassword("**********");
		   user.setGivenName(rs.getString("given_name"));
		   user.setSurname(rs.getString("surname"));
		   user.setIsActive(rs.getString("is_active"));
		   user.setRole(new Role(rs.getString("role_id"),rs.getString("role")));
		   users.add(user);
	   }
	   return users;
   }
   public void log(String level, String message) {
	   try {
		   File logFile = new File("logs/app.log");
		   logFile.createNewFile();
		   BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile));
		   logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
	       logWriter.flush();
	       
	   } catch(IOException e) {
		   throw new RuntimeException("There was a problem while writing to the log file");
	   }
   }
}
