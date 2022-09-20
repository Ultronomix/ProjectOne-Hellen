  package com.Hellen.MyProject.Reimbursements;

  import com.Hellen.MyProject.Common.Request;

  public class NewReimbRequest implements Request<Reimb>{
	
	 private String reimb_id;
	    private double amount;
	    private String description;
	    private String author_id; //? links to user_id  
	    private String type_id; //? links to reimbursement type

	    public String getReimb_id() {
	        return reimb_id;
	    }

	    public void setReimb_id(String reimb_id) {
	        this.reimb_id = reimb_id;
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public void setAmount(double amount) {
	        this.amount = amount;
	    }


	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	  

	    public String getAuthor_id() {
	        return author_id;
	    }

	    public void setAuthor_id(String author_id) {
	        this.author_id = author_id;
	    }

	    public String getType_id() {
	        return type_id;
	    }

	    public void setType_id(String type_id) {
	        this.type_id = type_id;
	    }

	    

	    @Override
	    public String toString() {
	        return "NewReimbRequest {" + 
	                "reimb_id = '" + reimb_id + '\'' + 
	                "amount = '" + amount + '\'' +
	                "description = '" + description + '\'' +	                
	                "author_id = '" + author_id + '\'' +
	                "type_id = '" + type_id + '\'' +
	                '}';
	    }

	    @Override
	    public Reimb extractEntity() {
	        Reimb extractEntity = new Reimb();
	        extractEntity.setReimb_id(this.reimb_id);
	        extractEntity.setAmount(this.amount);
	        extractEntity.setDescription(this.description);	        
	        extractEntity.setAuthor_id(this.author_id);
	        extractEntity.setType_id(this.type_id);
	        return null;
	    }

}
