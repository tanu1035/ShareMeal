package com.sharemeal.repository;

import com.sharemeal.entity.Pickup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupRepository extends JpaRepository<Pickup, Long> {
}
