package com.study.model;

import com.study.dto.CommentDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter
@Data
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    private String comment;

    private String commentWriter;

    public Comment(CommentDto commentDto){
        this.comment = commentDto.getComment();
        this.commentWriter = commentDto.getCommentWriter();
    }
}
