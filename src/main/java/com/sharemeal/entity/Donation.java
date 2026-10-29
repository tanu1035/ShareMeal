package com.sharemeal.entity;

import com.sharemeal.enums.DonationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter
@Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "donor_id")
    private User donor;

    @Column(name = "food_name", nullable = false, length = 100)
    private String foodName;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(name = "prepared_time")
    private LocalDateTime preparedTime;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "pickup_address", length = 255)
    private String pickupAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private DonationStatus status = DonationStatus.AVAILABLE;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
