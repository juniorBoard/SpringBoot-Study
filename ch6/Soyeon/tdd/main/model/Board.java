package com.study.model;

import com.study.dto.BoardDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@Entity
@Getter
@Data
@Builder
@AllArgsConstructor
public class Board {

    @Id
    private String boardId;

    private String boardTitle;

    private String boardMain;

    private String boardWriter;


    public Board(BoardDto boardDto){
        this.boardId = boardDto.getBoardId();
        this.boardTitle = boardDto.getBoardTitle();
        this.boardMain = boardDto.getBoardMain();
        this.boardWriter = boardDto.getBoardWriter();
    }

//    public Registry toEntity(){
//        return Registry.builder()
//                .id(registryId)
//                .title(registryTitle)
//                .main(registryMain)
//                .writer(registryWriter)
//                .build();
//    }

}
