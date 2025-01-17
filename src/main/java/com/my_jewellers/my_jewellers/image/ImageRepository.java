package com.my_jewellers.my_jewellers.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
//    Optional<Image> findFileByFileName(String fileName);
}
