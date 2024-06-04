package com.example.visioners.repository;

import com.example.visioners.dto.ProhibitedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProhibitedItemRepository extends JpaRepository<ProhibitedItem, Long> {
    ProhibitedItem findByName(String name);
}
