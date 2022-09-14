package com.example.tddprac.repository;

import com.example.tddprac.domain.Music;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MusicRepositoryTest {

    @Autowired
    MusicRepository musicRepository;

    @Test
    void saveTest() {

        // given
        Music music = new Music();
        music.setTitle("Couch Potato");
        music.setArtist("Jakubi");
        music.setGenre("R&B_Soul");

        // when
        Music saveMusic = musicRepository.save(music);

        // then
        Assertions.assertEquals(music.getTitle(), saveMusic.getTitle());
        Assertions.assertEquals(music.getArtist(), saveMusic.getArtist());
        Assertions.assertEquals(music.getGenre(), saveMusic.getGenre());

    }

    @Test
    void getTest() {

        // given
        Music music = new Music();
        music.setTitle("Couch Potato");
        music.setArtist("Jakubi");
        music.setGenre("R&B_Soul");
        musicRepository.save(music);

        // when
        Music getMusic = musicRepository.findByTitle("Couch Potato");

        // then
        Assertions.assertEquals(music.getTitle(), getMusic.getTitle());
        Assertions.assertEquals(music.getArtist(), getMusic.getArtist());
        Assertions.assertEquals(music.getGenre(), getMusic.getGenre());
    }

    @Test
    void deleteTest() {
        // given
        Music music = new Music();
        music.setTitle("Couch Potato");
        music.setArtist("Jakubi");
        music.setGenre("R&B_Soul");
        musicRepository.save(music);

        // when
        musicRepository.delete(music);
        Music getMusic = musicRepository.findByTitle("Couch Potato");

        // then
        Assertions.assertNull(getMusic);
    }
}