package com.example.tddprac.domain;

import com.example.tddprac.dto.MusicDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String genre;

    public Music(MusicDto musicDto) {
        this.title = musicDto.getTitle();
        this.artist = musicDto.getArtist();
        this.genre = musicDto.getGenre();
    }

}
