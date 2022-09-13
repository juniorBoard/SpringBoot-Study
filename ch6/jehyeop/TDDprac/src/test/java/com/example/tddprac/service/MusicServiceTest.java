package com.example.tddprac.service;

import com.example.tddprac.domain.Music;
import com.example.tddprac.dto.MusicDto;
import com.example.tddprac.dto.MusicResponseDto;
import com.example.tddprac.repository.MusicRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MusicServiceTest {

    @MockBean
    private MusicRepository musicRepository = Mockito.mock(MusicRepository.class);

    @MockBean
    private MusicService musicService;

    @BeforeEach
    public void setUpTest() {
        musicService = new MusicService(musicRepository);
    }

    @Test
    @Order(1)
    @DisplayName("getMusic() - title로 조회")
    void getMusicTest() {

        // given
        // 기본적인 값 설정
        Music givenMusic = new Music(1L, "Stay", "Post Malone", "Acoustic_Music");

        // Repository 역할 설정
        Mockito.when(musicRepository.findByTitle("Stay")).thenReturn(givenMusic);

        // when
        // Service 메소드 실행
        MusicResponseDto musicResponseDto = musicService.getMusic("Stay");

        // then
        // 값이 맞는지 확인
        Assertions.assertEquals(musicResponseDto.getId(), givenMusic.getId());
        Assertions.assertEquals(musicResponseDto.getTitle(), givenMusic.getTitle());
        Assertions.assertEquals(musicResponseDto.getArtist(), givenMusic.getArtist());
        Assertions.assertEquals(musicResponseDto.getGenre(), givenMusic.getGenre());

        // 메소드가 잘 동작하는지 확인
        verify(musicRepository).findByTitle("Stay");

    }

    @Test
    @Order(2)
    @DisplayName("saveMusic() - 노래저장")
    void saveMusicTest() {

        // given
        Mockito.when(musicRepository.save(any(Music.class)))
                .then(returnsFirstArg());

        // when
        MusicResponseDto musicResponseDto = musicService.saveMusic(
                new MusicDto("Couch Potato", "Jakubi", "R&B_Soul"));

        // then
        Assertions.assertEquals(musicResponseDto.getTitle(), "Couch Potato");
        Assertions.assertEquals(musicResponseDto.getArtist(), "Jakubi");
        Assertions.assertEquals(musicResponseDto.getGenre(), "R&B_Soul");

        verify(musicRepository).save(any());
    }

    @Test
    @Order(3)
    @DisplayName("modifyMusic() - 노래정보 수정")
    void modifyMusic() {
        // give
        // 기본 값 설정
        Music music = new Music(2L, "Couch Potato", "Jakubi", "alternative/indie");
        musicRepository.save(music);

        // 수정 후 값
        Music music2 = new Music(2L, "Couch Potato", "Jakubi", "R&B_Soul");

        Mockito.when(musicRepository.findByTitle(music.getTitle())).thenReturn(music2);

        Mockito.when(musicRepository.save(any(Music.class))).then(returnsFirstArg());

        // when
        MusicResponseDto musicResponseDto = musicService.modifyMusic(
                new MusicDto("Couch Potato", "Jakubi", "R&B_Soul"));

        // then
        Assertions.assertEquals(musicResponseDto.getId(), music2.getId());
        Assertions.assertEquals(musicResponseDto.getTitle(), music2.getTitle());
        Assertions.assertEquals(musicResponseDto.getArtist(), music2.getArtist());
        Assertions.assertEquals(musicResponseDto.getGenre(), music2.getGenre());

        verify(musicRepository).findByTitle(music.getTitle());
    }

    @Test
    @Order(4)
    @DisplayName("deleteMusic() - 노래정보 삭제")
    void deleteMusic() {
        // given
        // 삭제 전 값 설정
        Music music = new Music(2L, "Couch Potato", "Jakubi", "alternative/indie");
        musicRepository.save(music);

        // when
        String answer = musicService.deleteMusic("Couch Potato");

        // then
        Assertions.assertEquals(answer, "삭제 완료");

    }

}