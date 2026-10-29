package com.sharemeal.repository;

import com.sharemeal.entity.Donation;
import com.sharemeal.entity.FoodRequest;
import com.sharemeal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {

    List<FoodRequest> findByNgo(User ngo);

    List<FoodRequest> findByDonation(Donation donation);
}
