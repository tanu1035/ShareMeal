package com.sharemeal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pickups")
@Getter
@Setter
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "request_id", unique = true)
    private FoodRequest request;

    @Column(name = "picked_up_at", nullable = false)
    private LocalDateTime pickedUpAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "confirmed_by")
    private User confirmedBy;
}
