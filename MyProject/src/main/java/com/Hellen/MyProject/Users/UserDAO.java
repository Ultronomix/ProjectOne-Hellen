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
	
    private String baseSelect = "select au.user_id, au.username, au.email, au._password,ur.role, au.role_id, " +
                                "au.given_name, au.surname, " +
    	                        "au.is_active, ur.role " +
    	                        "from ers_users au " +
		                        "join ers_user_roles ur " +
                                "on au.role_id = ur.role_id ";

   public List<User> getAllUsers() {
	   
	   String sql = baseSelect;
	   
	 List<User> allUsers = new ArrayList<>();
	 
	 try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		 
		 Statement state = conn.createStatement();
		 ResultSet rs = state.executeQuery(sql);
		 
		allUsers = mapResultSet(rs);
		 
	 } catch (SQLException e) {
		 System.err.println("Something went wrong communicating with the database");
		 e.printStackTrace();
	 }
	  return allUsers; 
	   
   }
   
  
   public Optional<User> findUserByUsernameAndPassword(String username, String password) {
	  
	   String sql = baseSelect +  " where au.username = ? and au._password = ?";
	                
			   
	  try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		   PreparedStatement psmt = conn.prepareStatement(sql);
		   psmt.setString(1, username);
		   psmt.setString(2, password);
		   ResultSet rs = psmt.executeQuery();
		   return mapResultSet(rs).stream().findFirst();
		   
	   } catch (SQLException e) {
		   e.printStackTrace();
		   throw new DataSourceException(e);
	   }
   }
	  
	  public Optional<User> findUserbyId(String id) {
		  
		   String sql = baseSelect + "au.userId = ?";
		                
				   
		  try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			   PreparedStatement psmt = conn.prepareStatement(sql);
			   psmt.setString(1, id);
			   ResultSet rs = psmt.executeQuery();
			   return mapResultSet(rs).stream().findFirst();
			   
		   } catch (SQLException e) {
			   throw new DataSourceException(e);
		   }
		  
   }
	  public Optional<User> findUserbyUsername(String username) {
		  
		   String sql = baseSelect + "au.username = ?";
		                
				   
		  try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			   PreparedStatement psmt = conn.prepareStatement(sql);
			   psmt.setString(1, username);
			   ResultSet rs = psmt.executeQuery();
			   return mapResultSet(rs).stream().findFirst();
			   
		   } catch (SQLException e) {
			   throw new DataSourceException(e);
		   }
		  
  }
	  
	  public Optional<User> findUserByRole(String role) {
		  
		   String sql = baseSelect + "au.User_role = ?";
		                
				   
		  try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			   PreparedStatement psmt = conn.prepareStatement(sql);
			   psmt.setString(1, role);
			   ResultSet rs = psmt.executeQuery();
			   return mapResultSet(rs).stream().findFirst();
			   
		   } catch (SQLException e) {
			   throw new DataSourceException(e);
		   }
		  
 }
	  
	  public Optional<User> findUserByEmail(String email) {
		  
		   String sql = baseSelect + "au.email = ?";
		                
				   
		  try (Connection conn = ConnectionFactory.getInstance().getConnection()){
			   PreparedStatement psmt = conn.prepareStatement(sql);
			   psmt.setString(1, email);
			   ResultSet rs = psmt.executeQuery();
			   return mapResultSet(rs).stream().findFirst();
			   
		   } catch (SQLException e) {
			   throw new DataSourceException(e);
		   }
		  
}
	  
	  public boolean isEmailTaken(String email) {
		  return findUserByEmail(email).isPresent();
	  }
	  
	  public boolean isUsernameTaken(String username) {
		  return findUserbyUsername(username).isPresent();
	  }
	  
	  
 
   public String save(User user) {
	   String sql = "insert into ers_users(user_id, username, email, password, given_name, surname, is_active, role, role_id) " +
                    "values (?, ?, ?, ?, ?, ?, ? )";
	   
	   try (Connection conn = ConnectionFactory.getInstance().getConnection()){
		   
		   PreparedStatement pstmt = conn.prepareStatement(sql, new String[] {"id"});
		   System.out.println("username: " + user.getUsername());
		   pstmt.setString(1, user.getUsername());
		   System.out.println("email: " + user.getEmail());
		   pstmt.setString(2, user.getEmail());
		   System.out.println("password: " + user.getPassword());
		   pstmt.setString(3, user.getPassword());
		   System.out.println("givenName: " + user.getGivenName());
		   pstmt.setString(4, user.getGivenName());
		   System.out.println("surname: " + user.getSurname());
		   pstmt.setString(5, user.getSurname());
		   System.out.println("is_active: " + user.getIsActive());
		   pstmt.setBoolean(6, user.getIsActive());
		   System.out.println("role: " + user.getRole());
		   pstmt.setString(7, user.getRoleId());
		   
		   pstmt.executeUpdate();
		   
	   } catch(Exception e) {
		  System.err.println("Something went wrong when connecting to the database");
		  e.printStackTrace();
	   }
	   return user.getUsername();
	   }
	   
		   
		  
   
   private List<User> mapResultSet(ResultSet rs) throws SQLException {
	   List<User> users = new ArrayList();
	   while (rs.next()) {
		   User user = new User();
		   Role userRole = new Role();
		   
		   user.setUserId(rs.getString("user_id"));
		   user.setUsername(rs.getString("username"));
		   user.setEmail(rs.getString("email"));
		   user.setPassword("**********");
		   user.setGivenName(rs.getString("given_name"));
		   user.setSurname(rs.getString("surname"));
		   user.setIs_active(rs.getBoolean("is_active"));
		   
		   userRole.setId(rs.getString("role_id"));
		   userRole.setId(rs.getString("role"));
		   
		   user.setRole(rs.getString("role"));
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


     public String updateUserEmail(String email, String idToSearchFor) {
	        return "email changed";
     }
     public String updateUsername(String username, String idToSearchFor) {
    		return "username is changed";
     }
     public String updatePassword(String password, String idToSearchFor) {
    		return "password is changed";
     }
     public String updateGivenName(String givenName, String idToSearchFor) {
    		return "given name is changed";
     }
     public String updateIs_active(String is_active, String idToSearchFor) {
    		return "is_active has been changed";
     }
}
