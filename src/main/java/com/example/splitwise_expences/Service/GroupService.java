package com.example.splitwise_expences.Service;

import com.example.splitwise_expences.DTO.GroupRequest;
import com.example.splitwise_expences.DTO.GroupResponse;
import com.example.splitwise_expences.DTO.GroupEntity;
import com.example.splitwise_expences.Repository.GroupRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class GroupService {
    public final GroupRepository groupRepository;
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public GroupResponse createGroup(GroupRequest request) {
        // 1. Convert Request DTO into a Database Entity
        GroupEntity entity = new GroupEntity(request.name(), request.description());

        // 2. Save into PostgreSQL via the repository
        GroupEntity savedEntity = groupRepository.save(entity);

        // 3. Convert saved Entity back into a Response DTO
        return new GroupResponse(
                savedEntity.getId(),
                savedEntity.getName(),
                savedEntity.getDescription()
        );

    }
}
