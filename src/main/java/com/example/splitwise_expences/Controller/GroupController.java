package com.example.splitwise_expences.Controller;

import com.example.splitwise_expences.DTO.GroupRequest;
import com.example.splitwise_expences.DTO.GroupResponse;
import com.example.splitwise_expences.Service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupRequest request) {
        GroupResponse response = groupService.createGroup(request);
        return ResponseEntity.created(URI.create("/api/groups/" + response.id()) ).body(response);
    }
}