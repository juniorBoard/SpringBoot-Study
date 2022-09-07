package com.example.mini.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String author;
    private String title;
    private String description;
    private int thumb;
    @CreatedDate
    private LocalDateTime created_timestamp;

    public void like() {
        this.thumb++;
    }

    public void unlike() {
        validLikeZero();
        this.thumb--;
    }

    private void validLikeZero() {
        if (this.thumb <= 0) {
            throw new RuntimeException("좋아요 취소를 할 수 없습니다.");
        }
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateDescription(String description) {
        this.description = description;
    }
}
