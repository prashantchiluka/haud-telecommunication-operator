package com.haud.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created By: Amir Ansari
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Customer")
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;
    
    private String email;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id",referencedColumnName="id")
    private List<SimCard> simCards =  new ArrayList<>();
    
    @Column(name = "created_by", updatable = false, nullable = false, columnDefinition = "nvarchar(255)")
    private String createdBy;
    
    @Column(name = "created_on", updatable = false, nullable = false, columnDefinition = "datetime")
    private Timestamp createdOn;
    
    @Column(name = "updated_by", nullable = false, columnDefinition = "nvarchar(255)")
    private String updatedBy;
    
    @Column(name = "updated_on", nullable = false, columnDefinition = "datetime")
    private Timestamp updatedOn;
    
    @PrePersist
    public void onCreate() {
        this.createdOn = Timestamp.from(Instant.now());
        this.updatedOn = this.createdOn;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedOn = Timestamp.from(Instant.now());
    }
}
