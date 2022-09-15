package com.Hellen.MyProject.Reimbursements;

import com.Hellen.MyProject.Common.Request;

public class UpdateReimbRequest implements Request<Reimb>{
	
	
	    private int amount;
	    private String description;
	    private String _type;
	    private String status;
	    
	    
	    public String get_type() {
			return _type;
		}

		public void set_type(String _type) {
			this._type = _type;
		}
	    
		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		public String getDescription() {
			return description;
		}
	    
		public void setDescription(String description) {
			this.description = description;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}
		
		  @Override
		    public String toString() {
		        return "UpdateReimbRequest {" +
		               ",status = '" + status +
		               ",_type=' '" + _type + 
		               ", amount='" + amount +
		               ",description='" + description +
		               '}';
		    }

		    @Override
		    public Reimb extractEntity() {
		        Reimb extractedEntity = new Reimb();
		        extractedEntity.setAmount(this.amount);
		        extractedEntity.setStatus(this.status);
		        extractedEntity.setType(this._type);
		        extractedEntity.setDescription(this.description);
		        return extractedEntity;
		    }

			
	
}
