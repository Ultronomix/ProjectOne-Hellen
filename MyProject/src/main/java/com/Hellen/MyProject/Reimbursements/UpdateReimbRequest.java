package com.Hellen.MyProject.Reimbursements;

import com.Hellen.MyProject.Common.Request;

public class UpdateReimbRequest implements Request<Reimb>{
	
	
	    private double amount;
	    private String description;
	    private String reimb_id;
	    private String status_id;
	    private String author_id;
	    private String type_id;
	    
	    
	    
		 public String getStatus_id() {
				return status_id;
			}

			public void setStatus_id(String status_id) {
				this.status_id = status_id;
			}
			 public String getReimb_d() {
					return reimb_id;
				}

				public void setReimb_id(String reimb_id) {
					this.reimb_id = reimb_id;
				}
				
				public String getType_id() {
					return type_id;
				}

				public void setType_id(String type_id) {
					this.type_id = type_id;
				}
		
		public String getDescription() {
			return description;
		}
	    
		public void setDescription(String description) {
			this.description = description;
		}
		
		 public String getAuthor_d() {
				return author_id;
			}

			public void setAuthor_id(String author_id) {
				this.author_id = author_id;
			}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}
		
		  @Override
		    public String toString() {
		        return "UpdateReimbRequest {" + 
		               ",amount='" + amount +
		               ",author_id='" + author_id +
		               ",reimb_id='" + reimb_id +
		               ",status_id='" + status_id +
		               ",type_id='" + type_id +
		               ",description='" + description +
		               '}';
		    }

		    @Override
		    public Reimb extractEntity() {
		        Reimb extractedEntity = new Reimb();
		        extractedEntity.setAmount(this.amount);
		        extractedEntity.setStatus_id(this.status_id);
		        extractedEntity.setType_id(this.type_id);
		        extractedEntity.setDescription(this.description);
		        return extractedEntity;
		    }

			
	
}
