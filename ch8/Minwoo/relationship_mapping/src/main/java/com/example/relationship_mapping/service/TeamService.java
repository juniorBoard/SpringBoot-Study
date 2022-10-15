package com.example.relationship_mapping.service;

import com.example.relationship_mapping.domain.Member;
import com.example.relationship_mapping.domain.Team;
import com.example.relationship_mapping.dto.TeamReqDTO;
import com.example.relationship_mapping.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team create(TeamReqDTO teamReqDTO) {
        Team team = new Team();
        team.setName(teamReqDTO.getName());
        return teamRepository.save(team);
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public List<Member> getMembers(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new IllegalArgumentException()).getMembers();
    }
}
