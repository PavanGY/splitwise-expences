package com.example.splitwise_expences.Repository;

import com.example.splitwise_expences.DTO.GroupEntity;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
    // Spring Data JPA automatically implements basic CRUD operations here!
}
