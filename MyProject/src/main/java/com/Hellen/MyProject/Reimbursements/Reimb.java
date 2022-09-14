package com.Hellen.MyProject.Reimbursements;

import java.util.Objects;

public class Reimb {
	private String reimb_id;
	private String resolved;
	private String payment_id;
	private String submitted;
	private int amount;
	private String resolver_id;
	private String status;
	private String author_id;
	private String type;
	private String description;
	
	
	public String getReimb_id() {
		return reimb_id;
	}
	public void setReimb_id(String reimb_id) {
		this.reimb_id = reimb_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getSubmitted() {
		return submitted;
	}
	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}
	public String getResolved() {
		return resolved;
	}
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getResolver_id() {
		return resolver_id;
	}
	public void setResolver_id(String resolver_id) {
		this.resolver_id = resolver_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(reimb_id, amount, submitted, resolved, description, payment_id, author_id, resolver_id, status, type);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {return  true;}
		if(o == null || getClass() != o.getClass()) {return false;}
		Reimb reimbursement = (Reimb) o;
		return Objects.equals(reimb_id, reimbursement.reimb_id) && Objects.equals(amount, reimbursement.amount)
			&& Objects.equals(submitted, reimbursement.submitted) && Objects.equals(resolved, reimbursement.resolved)
			&& Objects.equals(description, reimbursement.description) && Objects.equals(payment_id, reimbursement.payment_id)
			&& Objects.equals(author_id, reimbursement.author_id) && Objects.equals(resolver_id, reimbursement.resolver_id)
			&& Objects.equals(status, reimbursement.status) && Objects.equals(type, reimbursement.type);
	}
	
	@Override
	public String toString() {
		return "Reimbursement {" +
	           " reimb_id='" + reimb_id + "' " +
			   "amount='" + amount + "' " +
	           "submitted='" + submitted + "' " +
			   "resolved='" + resolved + "' " +
	           "description='" + description + "' " +
			   "payment_id='" + payment_id + "' " +
	           "author_id='" + author_id + "' " +
			   "resolver_id='" + resolver_id + "' " +
	           "status='" + status + "' " +
			   "type='" + type + "' " +
	           '}';
	           
	}

}
