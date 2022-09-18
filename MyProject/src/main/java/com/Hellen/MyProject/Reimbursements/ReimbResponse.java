package com.Hellen.MyProject.Reimbursements;

import java.util.Objects;
import com.Hellen.MyProject.Users.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ReimbResponse implements Serializable{
	private String reimb_id;
    private double amount;
    private LocalDateTime submitted;
    private LocalDateTime resolved;
    private String description;
    private String payment_id;
    private String author_id; // ? links to user_id
    private String resolver_id; // ? links to user_id
    private String status_id; // ? links to reimbursement statuses
    private String type_id; // ? links to reimbursement type

    public ReimbResponse (Reimb subject) {
        this.reimb_id = subject.getReimb_id();
        this.amount = subject.getAmount();
        this.submitted = subject.getSubmitted();
        this.resolved = subject.getResolved();
        this.description = subject.getDescription();
        this.payment_id = subject.getPayment_id();
        this.author_id = subject.getAuthor_id();
        this.resolver_id = subject.getResolver_id();
        this.status_id = subject.getStatus_id();
        this.type_id = subject.getType_id();
    }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ||getClass() != o.getClass()) return false;
        ReimbResponse reimbResponse = (ReimbResponse) o;
        return amount == reimbResponse.amount && Objects.equals(reimb_id, reimbResponse.reimb_id) && Objects.equals(submitted, reimbResponse.submitted) && Objects.equals(resolved, reimbResponse.resolved) && Objects.equals(description, reimbResponse.description) && Objects.equals(payment_id, reimbResponse.payment_id)&& Objects.equals(author_id, reimbResponse.author_id) && Objects.equals(resolver_id, reimbResponse.resolver_id)&& Objects.equals(status_id, reimbResponse.status_id) && Objects.equals(type_id, reimbResponse.type_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimb_id, amount, submitted, resolved, description, payment_id, author_id, resolver_id, status_id, type_id);                   
    }

    @Override
    public String toString() {
        return "ReimbResponse {" +
                "reimb_id = '" + reimb_id + '\'' +
                "amount = '" + amount + '\'' +
                "submitted = '" + submitted + '\'' +
                "resolved = '" + resolved + '\'' +
                "description = '" + description + '\'' +
                "payment_id = '" + payment_id + '\'' +
                "author_id = '" + author_id + '\'' +
                "resolver_id = '" + resolver_id + '\'' +
                "status = '" + status_id + '\'' +
                "type = '" + type_id + '\'' +
                '}';
    }
}
