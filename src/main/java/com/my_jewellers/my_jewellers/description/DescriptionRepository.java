package com.my_jewellers.my_jewellers.description;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<Description, Integer> {
//    Optional<Description> findDescriptionByDescriptionName(String productName);
}