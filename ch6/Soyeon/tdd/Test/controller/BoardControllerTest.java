package com.study.controller;

import com.google.gson.Gson;
import com.study.dto.BoardDto;
import com.study.model.Board;
import com.study.serviceImpl.BoardServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BoardController.class) // test할 controller 이름

@AutoConfigureMockMvc // 이 어노테이션을 통해 MockMvc를 Builder 없이 주입 받을 수 있다.
public class BoardControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// BoardController에서 잡고 있는 Bean 객체에 대해 Mock 형태의 객체를 설명해준다.
	@MockBean
	BoardServiceImpl boardService;


	//http://localhost:8080/registry?boardId={boardId}
	@Test
	void getRegistry() throws Exception {
		given(boardService.getBoard("23")).willReturn(
				new Board("23", "title", "main", "writer")
		);

		String boardId = "23";

		mockMvc.perform(get("/registry?boardId=" + boardId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.boardId").exists())
				.andExpect(jsonPath("$.boardTitle").exists())
				.andExpect(jsonPath("$.boardMain").exists())
				.andExpect(jsonPath("$.boardWriter").exists())
				.andDo(print());

		// verify : 해당 객체의 메소드가 실행되었는지 체크해준다.
		verify(boardService).getBoard("23");
	}


	//http://localhost:8080/registry
	@Test
	void postRegistry() throws Exception {
		given(boardService.saveBoard(any(BoardDto.class)))
				.willReturn(new Board("23", "title", "main", "writer"));

		BoardDto boardDto = BoardDto.builder()
				.boardId("23")
				.boardTitle("title")
				.boardMain("main")
				.boardWriter("writer")
				.build();

		Gson gson = new Gson(); //google에서 만든 json의 형태를 자유롭게 다룰 수 있는 라이브러리(build.gradle)
		String content = gson.toJson(boardDto);
		mockMvc.perform(
				post("/registry")
						.content(content)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.boardId").exists())
				.andExpect(jsonPath("$.boardTitle").exists())
				.andExpect(jsonPath("$.boardMain").exists())
				.andExpect(jsonPath("$.boardWriter").exists())
				.andDo(print());

		verify(boardService).saveBoard(any(BoardDto.class));

	}

}
