package com.example.tddprac.controller;

import com.example.tddprac.dto.MusicDto;
import com.example.tddprac.dto.MusicResponseDto;
import com.example.tddprac.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @GetMapping("/music")
    public ResponseEntity<MusicResponseDto> getMusic(String title) {
        MusicResponseDto musicResponseDto = musicService.getMusic(title);
        return ResponseEntity.status(HttpStatus.OK).body(musicResponseDto);
    }

    @PostMapping("/music")
    public ResponseEntity<MusicResponseDto> saveMusic(@RequestBody MusicDto musicDto) {
        MusicResponseDto musicResponseDto = musicService.saveMusic(musicDto);
        return ResponseEntity.status(HttpStatus.OK).body(musicResponseDto);
    }

    @PutMapping("/music/modification")
    public ResponseEntity<MusicResponseDto> putMusic(@RequestBody MusicDto musicDto) {
        MusicResponseDto musicResponseDto = musicService.modifyMusic(musicDto);
        return ResponseEntity.status(HttpStatus.OK).body(musicResponseDto);
    }

    @DeleteMapping("/music/delete")
    public ResponseEntity<String> deleteMusic(String title) {
        musicService.deleteMusic(title);
        return ResponseEntity.status(HttpStatus.OK).body("삭제 완료");
    }
}
