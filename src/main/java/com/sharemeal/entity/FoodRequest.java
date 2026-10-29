package com.sharemeal.entity;

import com.sharemeal.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
public class FoodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ngo_id")
    private User ngo;

    @Column(name = "requested_qty", nullable = false)
    private int requestedQty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @PrePersist
    void onCreate() {
        requestedAt = LocalDateTime.now();
    }
}
