package com.locngo.loansystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    private static final long serialVersionUID = 7886719270859546917L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
    @SequenceGenerator(sequenceName = "payment_sequence", name = "payment_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "repayment", nullable = false)
    private Boolean repayment = false;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_repayment_id")
    private Payment ownerRepayment;

    @OneToMany(mappedBy = "ownerRepayment")
    private Set<Payment> repayments = new HashSet<>();

    @Embedded
    private BaseEntity baseEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getRepayment() {
        return repayment;
    }

    public void setRepayment(Boolean repayment) {
        this.repayment = repayment;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Payment getOwnerRepayment() {
        return ownerRepayment;
    }

    public void setOwnerRepayment(Payment ownerRepayment) {
        this.ownerRepayment = ownerRepayment;
    }

    public Set<Payment> getRepayments() {
        return repayments;
    }

    public void setRepayments(Set<Payment> repayments) {
        this.repayments = repayments;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
