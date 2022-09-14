package com.example.tddprac.repository;

import com.example.tddprac.domain.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music, Long> {
    Music findByTitle(String title);
    Music deleteByTitle(String title);
}
