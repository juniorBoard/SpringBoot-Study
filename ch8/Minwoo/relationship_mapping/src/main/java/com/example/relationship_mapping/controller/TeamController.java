package com.example.relationship_mapping.controller;

import com.example.relationship_mapping.domain.Member;
import com.example.relationship_mapping.domain.Team;
import com.example.relationship_mapping.dto.TeamReqDTO;
import com.example.relationship_mapping.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("")
    public Team create(@RequestBody TeamReqDTO teamReqDTO) {
        return teamService.create(teamReqDTO);
    }

    @GetMapping("/{team_id}")
    public Team findById(@PathVariable("team_id") Long id) {
        return teamService.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    @GetMapping("/{team_id}/members")
    public List<Member> getMembers(@PathVariable("team_id") Long id) {
        return teamService.getMembers(id);
    }
}
