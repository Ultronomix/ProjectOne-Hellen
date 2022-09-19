package com.Hellen.MyProject.Reimbursements;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reimb {
	
	private String reimb_id;
	private LocalDateTime resolved;
	private String payment_id;
	private LocalDateTime submitted;
	private double amount;
	private String resolver_id;
	private String status_id;
	private String author_id;
	private String type_id;
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
	public LocalDateTime getSubmitted() {
		return submitted;
	}
	public void setSubmitted(LocalDateTime submitted) {
		this.submitted = submitted;
	}
	public LocalDateTime getResolved() {
		return resolved;
	}
	public void setResolved(LocalDateTime resolved) {
		this.resolved = resolved;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getResolver_id() {
		return resolver_id;
	}
	public void setResolver_id(String resolver_id) {
		this.resolver_id = resolver_id;
	}
	public String getStatus_id() {
		return status_id;
	}
	public void setStatus_id(String status_id) {
		this.status_id = status_id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(reimb_id, amount, submitted, resolved, description, payment_id, author_id, resolver_id, status_id, type_id);
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return  true;
		if(o == null || getClass() != o.getClass()) return false;
		Reimb reimbursement = (Reimb) o;
		return Double.compare(reimbursement.amount, amount) == 0 && Objects.equals(reimb_id, reimbursement.reimb_id)&& Objects.equals(submitted, reimbursement.submitted) && Objects.equals(resolver_id, reimbursement.resolver_id) && Objects.equals(description, reimbursement.description) && Objects.equals(payment_id, reimbursement.payment_id) && Objects.equals(author_id, reimbursement.author_id) && Objects.equals(resolved, reimbursement.resolved) && Objects.equals(status_id, reimbursement.status_id) && Objects.equals(type_id, reimbursement.type_id);
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
	           "status='" + status_id + "' " +
			   "type='" + type_id + "' " +
	           '}';
	           
	}

}
