package com.example.tddprac.controller;

import com.example.tddprac.domain.Music;
import com.example.tddprac.dto.MusicDto;
import com.example.tddprac.dto.MusicResponseDto;
import com.example.tddprac.repository.MusicRepository;
import com.example.tddprac.service.MusicService;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MusicController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MusicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    MusicService musicService; // 주입 받고있는 Service를 임시로 만들어 사용

    @MockBean
    MusicRepository musicRepository;

    // http://localhost:8080/music?title=/{title}
    @Test
    @Order(1)
    @DisplayName("GET TEST - title로 조회")
    void getMusicTest() throws Exception {

        // given : Mock 객체가 특정 상황에서 해야하는 행위를 정의
        given(musicService.getMusic("Stay"))
                .willReturn(new MusicResponseDto(1L, "Stay", "Post Malone","Acoustic_Music"));

        String title = "Stay";

        // when
        // and Expect :  기대하는 값이 나왔는지 체크
        // jsonPath : json path의 depth가 깊어지면 . 을 추가하여 탐색
        mockMvc.perform(
                        get("/music?title=" + title)) // 경로
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()) // json의 key 값을 조회할수 있다.
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.genre").exists())
                .andDo(print()); // 위에서 테스트 한걸 프린트 해준다.

        // then
        // verify : 해당 객체의 메소드(getTitle)가 실행되었는지 체크
        verify(musicService).getMusic("Stay");
    }

    @Test
    @Order(2)
    @DisplayName("POST TEST - 노래 저장")
    void createMusic() throws Exception {

        // given
        // Mock : 객체에서 특정 메소드가 실행되는 경우 실제 Return을 줄 수 없기 때문에 가정 사항을 만들어준다.
        given(musicService.saveMusic(any(MusicDto.class)))
                .willReturn(new MusicResponseDto(2L, "Couch Potato", "Jakubi", "R&B_Soul"));

        // Dto 정의
        MusicDto musicDto = MusicDto.builder()
                .title("Couch Potato")
                .artist("Jakubi")
                .genre("R&B_Soul")
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(musicDto); // Dto 객체를 json 형태로 변경 1

        // String json = new ObjectMapper().writeValueAsString(musicDto); // Dto 객체를 json 형태로 변경 2

        // when
        // content : 어떠한 body 값을 넘겨줄지 타입과 함꼐 정한다.
        mockMvc.perform(
                        post("/music").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.genre").exists())
                .andDo(print());

        // then
        verify(musicService).saveMusic(any(MusicDto.class));
    }

    @Test
    @Order(3)
    @DisplayName("PUT TEST - 저장한 노래 장르 수정")
    void modifyMusic() throws Exception {

        // given
        // 임의로 저장소에 값을 저장해준다.
        MusicDto musicDto = new MusicDto("Couch Potato", "Jakubi", "alternative/indie");
        Music music = new Music(musicDto);
        musicRepository.save(music);

        // 요청값과 기대값을 적어준다.
        given(musicService.modifyMusic(any(MusicDto.class)))
                .willReturn(new MusicResponseDto(1L, "Couch Potato", "Jakubi", "R&B_Soul"));

        // Dto 정의
        MusicDto musicDto2 = MusicDto.builder()
                .title("Couch Potato")
                .artist("Jakubi")
                .genre("R&B_Soul")
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(musicDto2); // Dto 객체를 json 형태로 변경

        // when
        // content : 어떠한 body 값을 넘겨줄지 타입과 함꼐 정한다.
        mockMvc.perform(
                        put("/music/modification").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.genre").exists())
                .andDo(print());

        // then
        verify(musicService).modifyMusic(any(MusicDto.class));
    }

    @Test
    @Order(4)
    @DisplayName("DELETE - 노래 삭제")
    void deleteMusic() throws Exception {

        // given
        // 임의로 저장소에 값을 저장해준다.
        MusicDto musicDto = new MusicDto("Couch_Potato", "Jakubi", "alternative/indie");
        Music music = new Music(musicDto);
        musicRepository.save(music);

        given(musicService.deleteMusic("Couch_Potato")).willReturn("삭제 완료");

        String title = "Couch_Potato";

        //when
        mockMvc.perform(
                        delete("/music/delete?title="+ title))
                .andExpect(status().isOk())
                .andDo(print()); // 위에서 테스트 한걸 프린트 해준다.

        // then
        verify(musicService).deleteMusic("Couch_Potato");

    }
}