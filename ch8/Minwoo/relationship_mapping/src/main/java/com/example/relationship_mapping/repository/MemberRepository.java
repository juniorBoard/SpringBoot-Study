package com.example.relationship_mapping.repository;

import com.example.relationship_mapping.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    long countById(Long id);
}
