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

public class ReimbDAO {
	
	private final String select = "SELECT er.reimb_id, er.amount, er.submitted, er.resolved, " +
                                  "er.description, er.payment_id, er.author_id, er.resolver_id, " +
                                  "ers.status, ert._type " +
                                  "FROM ers_reimbursements er " +
                                  "JOIN ers_reimbursement_statuses ers ON er.status_id = ers.status_id " +
                                  "JOIN ers_reimbursement_types ert ON er.type_id = ert.type_id ";

    public List<Reimb> getAllReimb () {

       List<Reimb> allreimbs = new ArrayList<>();

       try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(select);

      allreimbs = mapResultSet(rs);

      return allreimbs;

      } catch (SQLException e) {
      // TODO add log
      throw new DataSourceException(e);
}

}

     public Optional<Reimb> getReimbByReimbId (String reimb_id) {

     // TODO add log
    String sqlId = select + "WHERE er.author_id = ?";

    try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

        PreparedStatement pstmt = conn.prepareStatement(sqlId);
        pstmt.setString(1, reimb_id);
        ResultSet rs = pstmt.executeQuery();

   return mapResultSet(rs).stream().findFirst();
   // TODO add log
   } catch (SQLException e) {
   // TODO add log
   throw new DataSourceException(e);
   }

   }

   public List<Reimb> getReimbByStatus (String status) {

   // TODO add log
   String sqlStatus = select + "WHERE ers.status = ?";
   List<Reimb> reimbsStatus = new  ArrayList<>();

   try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement(sqlStatus);
      pstmt.setString(1, status.toUpperCase());
      ResultSet rs = pstmt.executeQuery();

      reimbsStatus = mapResultSet(rs);

      return reimbsStatus;
      // TODO add log
      } catch (SQLException e) {
      // TODO add log
      throw new DataSourceException(e);
     }
     }

    public List<Reimb> getReimbByType (String type) {

    // TODO add log
    String sqlType = select + "WHERE ert.type = ?";
    List<Reimb> reimbsType = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

       PreparedStatement pstmt = conn.prepareStatement(sqlType);
       pstmt.setString(1, type.toUpperCase());
       ResultSet rs = pstmt.executeQuery();

       reimbsType = mapResultSet(rs);

        return reimbsType;
        // TODO add log
        } catch (Exception e) {
        // TODO add log
        throw new DataSourceException(e);
       }
      }
      
      public String updateRequestStatus (String status, String reimb_id, String resolver_id) {
    	  String updateSql = "update ers_reimbursements set status_id = ?, resolved = ?, resolver_id = ?, where remb_id = ?";
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(updateSql);
    		  pstmt.setString(1, status);
    		  pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
    		  pstmt.setString(3,resolver_id);
    		  pstmt.setString(4, reimb_id);
    		  pstmt.executeUpdate();
    		  return "Updated status";
    		  
    	  } catch (SQLException e) {
    		  throw new DataSourceException(e);
    	  }
      }
      public String updateUserAmount (String reimbId,double newAmount) {
    	  String updateAmountSql = "update ers_reimbursements set amount = ? where reimb_id = ?";
    	  
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
    		  pstmt.setDouble(1, newAmount);
    		  pstmt.setString(2, reimbId);
    		  pstmt.executeUpdate();
    		  return "Amount";
    	  } catch(SQLException e) {
    		  throw new DataSourceException(e);
    	  }
      }
      
      public String updateUserDescription (String reimbId, String description) {
    	  
         String updateAmountSql = "update ers_reimbursements set description = ? where reimb_id = ?";
    	  
    	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
    		  
    		  PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
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
    	  
          String updateAmountSql = "update ers_reimbursements set type_id = ? where reimb_id = ?";
     	  
     	  try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
     		  
     		  PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
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
    		  if(reimb.get().getStatus().equals("Pending")) {
    			  return true;
    		  } else {
    			  return false;
    		  }
    	  } catch (NoSuchElementException e) {
    		  throw new ResourceNotFoundException();
    	  }
      }
      

	private List<Reimb> mapResultSet(ResultSet rs) throws SQLException {

     List<Reimb> reimbs = new ArrayList<>();

   while (rs.next()) {
     Reimb reimb = new Reimb();
     reimb.setReimb_id(rs.getString("reimb_id"));
     reimb.setAmount(rs.getDouble("amount"));
     reimb.setSubmitted(rs.getString("submitted"));
     reimb.setResolved(rs.getString("resolved"));
     reimb.setDescription(rs.getString("description"));
     reimb.setPayment_id(rs.getString("payment_id"));
     reimb.setAuthor_id(rs.getString("author_id"));
     reimb.setResolver_id(rs.getString("resolver_id"));
     reimb.setStatus(rs.getString("status"));
     reimb.setType(rs.getString("_type"));
     reimbs.add(reimb);
    }

    return reimbs;
   }
	
	 public void log(String level, String message) {
	        try {
	            File logFile = new File("logs/app.log");
	            logFile.createNewFile();
	            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile));
	            logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
	            logWriter.flush();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	   
 }
