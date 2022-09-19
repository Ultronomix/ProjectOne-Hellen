package com.Hellen.MyProject.Reimbursements;

 import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Hellen.MyProject.Common.ErrorResponse;
import com.Hellen.MyProject.Exceptions.*;
import com.Hellen.MyProject.Users.UpdateUserRequest;
import com.Hellen.MyProject.Users.UserResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


 public class ReimbServlet extends HttpServlet {
	 
	//private static Logger logger = LogManager.getLogger(ReimbServlet.class);

	    private final ObjectMapper jsonMapper;
        private final ReimbService reimbService;
        
	    public ReimbServlet(ReimbService reimbService, ObjectMapper jsonMapper) {
	        this.reimbService = reimbService;
	        this.jsonMapper = jsonMapper;
	    }

	    @Override
	    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        
	        resp.setContentType("application/json");

	        HttpSession reimbSession = req.getSession(false);

	        if (reimbSession == null) {
	            
	            resp.setStatus(401);
	            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requestor not authenticated with server, log in")));
	            return;
	        }

	        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");

	        String reimb_idToSearchFor = req.getParameter("reimb_id");
	        String status_idToSearchFor = req.getParameter("status_id");
	        //String type_idToSearchFor = req.getParameter("type_id");

	        if ((!requester.getRole().equals("admin") && !requester.getRole().equals("finance manager")) && !requester.getRole().equals("employee")) { // && !requester.getRole().equals(idToSearchFor)) {
	            
	            resp.setStatus(403); // Forbidden
	            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint.")));
	            return;
	        }
	        
	        try {
	        	
	          if(reimb_idToSearchFor != null) {
	        	  
	        	  ReimbResponse foundRequest = reimbService.getReimbByReimb_id(reimb_idToSearchFor);
	        	  resp.getWriter().write(jsonMapper.writeValueAsString(foundRequest));
	          }
	          
	          if(status_idToSearchFor != null) {
	        	  ReimbResponse foundStatus_id = reimbService.getReimbByStatus_id(status_idToSearchFor);
	        	  resp.getWriter().write(jsonMapper.writeValueAsString(foundStatus_id));
	          }
	            
	            }catch(InvalidRequestException | JsonMappingException e) {
	            	
	            	resp.setStatus(400);
	            	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
	            
	            }catch (ResourceNotFoundException e) {
	            	
	            	resp.setStatus(404);
	            	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
	            
	            }catch(DataSourceException e) {
	            	
	            	resp.setStatus(500);
	            	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
	            }
	            

	        resp.getWriter().write("Reimb working!");
	    }

	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	       resp.setContentType("application/json");
           HttpSession reimbSession = req.getSession(false);
     
	        //HttpSession loggedInUserSession = req.getSession(false);
	        if (reimbSession  == null) {
	            resp.setStatus(401);
	            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Please log in first!")));
	            return;
	        }

	        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");
	        
	        if(!requester.getRole().equals("employee")) {
	        	//logger.warn("Requester with no permission attempted to register at {}", LocalDateTime.now());
	        	resp.setStatus(403);
	        	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint")));
	            return;
	        }
	        //logger.info("Attempting to register a new reimbursement at {}", LocalDateTime.now());
	        
	        try {
	        	
	        	NewReimbRequest requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbRequest.class);
	        	requestBody.setAuthor_id(requester.getId());
	        	ResourceCreationResponse responseBody = reimbService.create(requestBody);
	        	resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
	        	//logger.info("New reimbursement successfully persisted at {}", LocalDateTime.now());
	        	
	        } catch (InvalidRequestException | JsonMappingException e) {
	        	
	        	   resp.setStatus(400); // BAD REQUEST;
	               resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
	               //logger.warn("Unable to persist new reimbursement at {}, error message: {}", LocalDateTime.now(), e.getMessage());

	           } catch (ResourcePersistenceException e) {

	               resp.setStatus(409); // CONFLICT; indicates that the provided resource could not be saved without conflicting with other data
	               resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
	               //logger.warn("Unable to persist new reimbursement at {}, error message: {}", LocalDateTime.now(), e.getMessage());

	           } catch (DataSourceException e) {

	               resp.setStatus(500); // INTERNAL SERVER ERROR; general error indicating a problem with the server
	               resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
	               //logger.warn("Unable to persist new reimbursement at {}, error message: {}", LocalDateTime.now(), e.getMessage());

	           }
	        
	    }

	    @Override
	    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	        // TODO add log
	        ObjectMapper jsonMapper = new ObjectMapper();
	        resp.setContentType("application/json");

	        HttpSession reimbSession = req.getSession(false);

	        if (reimbSession == null) {
	            // TODO add log
	            resp.setStatus(401);
	            resp.getWriter().write(jsonMapper
	                    .writeValueAsString(new ErrorResponse(401, "Requestor not authenticated with server, log in")));
	            return;
	        }

	        UserResponse requester = (UserResponse) reimbSession.getAttribute("authUser");

	        String idToSearchFor= req.getParameter("id");

	        if ((!requester.getRole().equals("admin") && !requester.getRole().equals("finance manager")) 
	          && !requester.getId().equals(idToSearchFor)) {
	            resp.setStatus(403);
	            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint")));
	            return;
	        }

	        try {
	        	UpdateReimbRequest requestPayload = jsonMapper.readValue(req.getInputStream(), UpdateReimbRequest.class);
				reimbService.updateReimb(requestPayload);
				resp.setStatus(204);
			
	        		
	        }catch(InvalidRequestException |  JsonMappingException e) {
				resp.setStatus(400);
				resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
				
			} catch (AuthenticationException e) {
				resp.setStatus(409);
				resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
				
			} catch (DataSourceException e) {
				resp.setStatus(500);
				resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
			}

	        resp.getWriter().write("Put to /reimb work");
               }
 
	
}
 
