package com.example.relationship_mapping.repository;

import com.example.relationship_mapping.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
