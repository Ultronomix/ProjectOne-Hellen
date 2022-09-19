package com.Hellen.MyProject.Reimbursements;

import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.Hellen.MyProject.Common.ConnectionFactory;
import com.Hellen.MyProject.Exceptions.DataSourceException;
import com.Hellen.MyProject.Exceptions.ResourceNotFoundException;
import com.Hellen.MyProject.Users.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReimbDAO {
	
	private final String baseSelect = "SELECT er.reimb_id, er.amount, er.submitted, er.resolved, er.description, er.author_id, er.resolver_id, er.status_id, er.type_id, ers.status, ert.type, au.user_id " +
                                      "FROM ers_reimbursements er " +
                                      "JOIN ers_reimbursement_statuses ers " +
                                      "ON er.status_id = ers.status_id " +
                                      "JOIN ers_reimbursement_types ert " +
                                      "ON er.type_id = ert.type_id " +
                                      "JOIN ers_users eu " +
                                      "ON er.author_id = eu.user_id " +
                                      "LEFT JOIN ers_users " +
                                      "ON er.resolver_id = eu.user_id ";

    public List<Reimb> getAllReimb () {

       List<Reimb> allreimbs = new ArrayList<>();

       try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(baseSelect);

      allreimbs = mapResultSet(rs);

      } catch (SQLException e) {
      System.out.println("Something went wrong when connecting to the database");
      e.printStackTrace();
  }
       return allreimbs;

  }

     public Optional<Reimb> getReimbByReimbId (String reimb_id) {

     
    String sql = baseSelect + "WHERE er.reimb_id = ?";

    try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, reimb_id);
        ResultSet rs = pstmt.executeQuery();

   return mapResultSet(rs).stream().findFirst();
   
   } catch (SQLException e) {
   // TODO add log
   throw new DataSourceException(e);
   }

   }

   public Optional<Reimb> getReimbByStatus (String status) {

   // TODO add log
   String sql = baseSelect + "WHERE ers.status = ?";
   

   try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, status.toUpperCase());
      ResultSet rs = pstmt.executeQuery();

     

      return mapResultSet(rs).stream().findFirst();
      // TODO add log
      } catch (SQLException e) {
      // TODO add log
      throw new DataSourceException(e);
     }
     }
   
     

    public Optional<Reimb> getReimbByType (String type) {

    // TODO add log
    String sql = baseSelect + "WHERE ert.type = ?";
    
    try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

       PreparedStatement pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, type.toUpperCase());
       ResultSet rs = pstmt.executeQuery();

        return mapResultSet(rs).stream().findFirst();
        
        } catch (Exception e) {
        // TODO add log
        throw new DataSourceException(e);
       }
      }
      
      public void updateReimb (Reimb reimb) {
    	  
    	  String Sql = "update ers_reimbursements  " +
    	  		       "set amount = ?, description = ?, type_id = ? " +
    			       "where remb_id = ?";
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(Sql);
    		  pstmt.setDouble(1, reimb.getAmount());
    		  pstmt.setString(2, reimb.getType_id());
    		  pstmt.setString(3, reimb.getDescription());
    		  pstmt.setString(4, reimb.getReimb_id());
    		  
    		  int rowsUpdated = pstmt.executeUpdate();
    		  if(rowsUpdated != 1) {
    			  System.out.println("Nothing was updated");
    		  }
    		  
    	  } catch (SQLException e) {
    		  throw new DataSourceException(e);
    	  }
      }
      
      public String updateUserAmount (String reimbId, double newAmount) {
    	  
    	  String Sql = "update ers_reimbursements set amount = ? where reimb_id = ?";
    	  
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(Sql);
    		  pstmt.setDouble(1, newAmount);
    		  pstmt.setString(2, reimbId);
    		  pstmt.executeUpdate();
    		  return "Amount";
    		  
    	  } catch(SQLException e) {
    		  throw new DataSourceException(e);
    	  }
      }
      
      public String updateUserDescription (String reimbId, String description) {
    	  
         String Sql = "update ers_reimbursements set description = ? where reimb_id = ?";
    	  
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(Sql);
    		  pstmt.setString(1, description);
    		  pstmt.setString(2, reimbId);
    		  System.out.println(pstmt);
    		  pstmt.executeUpdate();
    		  
    		  return "Description";
    	  } catch(SQLException e) {
    		  throw new DataSourceException(e);
    	  }
      }
     
      public String updateUserType (String reimbId, String type_id) {
    	  
          String Sql = "update ers_reimbursements set type_id = ? where reimb_id = ?";
     	  
     	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
     		  
     		  PreparedStatement pstmt = conn.prepareStatement(Sql);
     		  pstmt.setString(1, type_id);
     		  pstmt.setString(2, reimbId);
     		  System.out.println(pstmt);
     		  pstmt.executeUpdate();
     		  
     		  return "Type";
     	  } catch(SQLException e) {
     		  throw new DataSourceException(e);
     	  }
       }
      
      public boolean isPending (String reimbId) {
    	  
    	  try {
    		  Optional<Reimb> reimb = getReimbByReimbId(reimbId);
    		  
    		  if(reimb.get().getStatus_id().equals("Pending")) {
    			  return true;
    		  } else {
    			  return false;
    		  }
    	  } catch (NoSuchElementException e) {
    		  
    		  throw new ResourceNotFoundException();
    	  }
      }
      
      public String save(Reimb newReimb) {

          //logger.info("Attempting to persist new reimbursement at {}", LocalDateTime.now());
          String sql = "INSERT INTO ers_reimbursements (reimb_id, amount, author_id, description, status_id, type_id) " +
                       "VALUES (5, ? , ?, ?, 3000, ?)";

          try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

             
        	  //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
              PreparedStatement pstmt = conn.prepareStatement(baseSelect);
              pstmt.setString(1, newReimb.getReimb_id());
              pstmt.setDouble(2, newReimb.getAmount());
              pstmt.setString(3, newReimb.getAuthor_id());
              pstmt.setString(4, newReimb.getDescription());
              pstmt.setString(5, newReimb.getType_id());
             
              
              pstmt.executeUpdate();

              ResultSet rs = pstmt.getGeneratedKeys();
              rs.next();
              newReimb.setReimb_id(rs.getString("reimb_id"));

          } catch (SQLException e) {
              e.printStackTrace();
          }

          return newReimb.getReimb_id();

      }

	private List<Reimb> mapResultSet(ResultSet rs) throws SQLException {

     List<Reimb> reimbs = new ArrayList<>();

   while (rs.next()) {
     Reimb reimb = new Reimb();
     reimb.setReimb_id(rs.getString("reimb_id"));
     reimb.setAmount(rs.getDouble("amount"));
     reimb.setSubmitted(rs.getTimestamp("submitted").toLocalDateTime());
     reimb.setResolver_id(rs.getString("resolver_id"));
     reimb.setDescription(rs.getString("description"));   
     reimb.setAuthor_id(rs.getString("author_id"));
     reimb.setResolved(rs.getTimestamp("resolved").toLocalDateTime());
     reimb.setStatus_id(rs.getString("status_id"));
     reimb.setType_id(rs.getString("type_id"));
     reimbs.add(reimb);
    }

    return reimbs;
   }
	
	
	   
 }
