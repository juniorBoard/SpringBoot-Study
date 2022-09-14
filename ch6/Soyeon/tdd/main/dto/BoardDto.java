package com.study.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	private String boardId;
	private String boardTitle;
	private String boardMain;
	private String boardWriter;
}
