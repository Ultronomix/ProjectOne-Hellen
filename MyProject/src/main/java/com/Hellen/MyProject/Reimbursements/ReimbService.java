package com.Hellen.MyProject.Reimbursements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Connection;
import com.Hellen.MyProject.Exceptions.InvalidRequestException;
import com.Hellen.MyProject.Exceptions.ResourceCreationResponse;
import com.Hellen.MyProject.Exceptions.ResourceNotFoundException;
import com.Hellen.MyProject.Exceptions.ResourcePersistenceException;
import com.Hellen.MyProject.Users.UpdateUserRequest;
import com.Hellen.MyProject.Users.UserDAO;

public class ReimbService {
	
	private final ReimbDAO reimbDAO;

    public ReimbService (ReimbDAO reimbDAO) {
        this.reimbDAO = reimbDAO;
    }
    
    public List<ReimbResponse> getAllReimb () {

    	return reimbDAO.getAllReimb().stream()
    			.map(ReimbResponse::new)
    			.collect(Collectors.toList());

    }

    public ReimbResponse getReimbByReimb_id (String reimb_id) {

        // TODO add log
        if (reimb_id == null || reimb_id.trim().length() <= 0) {
           
            throw new InvalidRequestException("A user's id must be provided");
        }
        try {
        	return reimbDAO.getReimbByReimbId(reimb_id)
        			.map(ReimbResponse::new)
        			.orElseThrow(ResourceNotFoundException::new);
        	
        } catch(IllegalArgumentException e) {
        	throw new InvalidRequestException("An invalid reimbursement Id was provided");
        }
        
    
    }

    public ReimbResponse getReimbByStatus_id (String status_id) {

        // TODO add log
        if (status_id == null || status_id.length() <= 0) {
        	throw new InvalidRequestException("Anon empty Id must be provided");
        }
          try { 
        	  return reimbDAO.getReimbByStatus(status_id)
        			  .map(ReimbResponse::new)
        			  .orElseThrow(ResourceNotFoundException::new);                  
        } catch(IllegalArgumentException e) {
        	throw new InvalidRequestException("An invalid status_id was provided");
        }
          
    }
        


    public ReimbResponse getReimbByType_id (String type_id) {

        
        if (type_id == null || (!type_id.toUpperCase().trim().equals("LODGING") 
                          && !type_id.toUpperCase().trim().equals("TRAVEL")
                          && !type_id.toUpperCase().trim().equals("FOOD")
                          && !type_id.toUpperCase().trim().equals("OTHER"))) {
            
            throw new InvalidRequestException("Type must be 'Lodging', 'Travel', 'Food' or 'Other'");
                                              
        }

       try {
    	   return reimbDAO.getReimbByType(type_id)
    			   .map(ReimbResponse::new)
    			   .orElseThrow(ResourceNotFoundException::new);
       
    } catch(IllegalArgumentException e) {
    throw new InvalidRequestException("An invalid type Id was provided");	
    
      }
    
    }

   
    public void updateReimb(UpdateReimbRequest updateReimb) {
    	
    	Reimb reimbToUpdate = reimbDAO.getReimbByReimbId(updateReimb.getReimb_d())
    			.orElseThrow(ResourceNotFoundException::new);
    	
    	if(updateReimb.getAmount() != 0) {
    		reimbToUpdate.setAmount(updateReimb.getAmount());
    	}
    	
    	if (updateReimb.getDescription() != null) {
    		reimbToUpdate.setDescription(updateReimb.getDescription());
    	}
    	
    	if (updateReimb.getType_id() != null) {
    		reimbToUpdate.setType_id(updateReimb.getType_id());
    	}
    	reimbDAO.updateReimb(reimbToUpdate);
    	
    }
    
    public ResourceCreationResponse create(NewReimbRequest newReimb) {
    	if(newReimb == null)
    		throw new InvalidRequestException("A new reimbursement request denied");
    
    
    if (newReimb.getAmount() == 0) {
        throw new InvalidRequestException("Provided request payload was null.");
   
    }

    if (newReimb.getDescription() == null) {
        throw new InvalidRequestException("Provided request payload was null.");
    }

    if (newReimb.getAuthor_id() == null) {
        throw new InvalidRequestException("Provided request payload was null.");
    }

    if (newReimb.getType_id() == null) {
        throw new InvalidRequestException("Provided request payload was null.");

    }
    
    Reimb reimbToPersist = newReimb.extractEntity();
    String newReimb_id = reimbDAO.save(reimbToPersist);
    return new ResourceCreationResponse(newReimb_id);
}

}
