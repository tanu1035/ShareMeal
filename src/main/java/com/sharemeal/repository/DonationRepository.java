package com.sharemeal.repository;

import com.sharemeal.entity.Donation;
import com.sharemeal.entity.User;
import com.sharemeal.enums.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByDonorOrderByCreatedAtDesc(User donor);

    // expiry-aware listing: only food that is still available and not yet expired
    List<Donation> findByStatusAndExpiryTimeAfter(DonationStatus status, LocalDateTime now);
}
