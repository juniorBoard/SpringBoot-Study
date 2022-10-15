package com.example.relationship_mapping.repository;

import com.example.relationship_mapping.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    long countById(Long id);
}
