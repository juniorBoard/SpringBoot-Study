package com.example.tddprac.service;

import com.example.tddprac.domain.Music;
import com.example.tddprac.dto.MusicDto;
import com.example.tddprac.dto.MusicResponseDto;
import com.example.tddprac.repository.MusicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;

    public MusicResponseDto getMusic(String title) {
        Music music = musicRepository.findByTitle(title);

        MusicResponseDto musicResponseDto = MusicResponseDto.builder()
                .Id(music.getId())
                .title(music.getTitle())
                .artist(music.getArtist())
                .genre(music.getGenre())
                .build();

        return musicResponseDto;
    }

    public MusicResponseDto saveMusic(MusicDto musicDto) {

        Music music = new Music();
        music.setTitle(musicDto.getTitle());
        music.setArtist(musicDto.getArtist());
        music.setGenre(musicDto.getGenre());

        Music saveMusic = musicRepository.save(music);

        MusicResponseDto musicResponseDto = MusicResponseDto.builder()
                .Id(saveMusic.getId())
                .title(saveMusic.getTitle())
                .artist(saveMusic.getArtist())
                .genre(saveMusic.getGenre())
                .build();

        return musicResponseDto;
    }

    public MusicResponseDto modifyMusic(MusicDto musicDto) {

        Music searchMusic = musicRepository.findByTitle(musicDto.getTitle());

        searchMusic.setGenre(musicDto.getGenre());

        Music saveMusic = musicRepository.save(searchMusic);

        MusicResponseDto musicResponseDto = MusicResponseDto.builder()
                .Id(saveMusic.getId())
                .title(saveMusic.getTitle())
                .artist(saveMusic.getArtist())
                .genre(saveMusic.getGenre())
                .build();

        return musicResponseDto;
    }

    public String deleteMusic(String title) {
        musicRepository.deleteByTitle(title);
        return "삭제 완료";
    }
}
