package com.haud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.*;

/**
 * Created By: Amir Ansari
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Simcard")
@Builder
public class SimCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long imsi;
    private long msisdn;
    
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
