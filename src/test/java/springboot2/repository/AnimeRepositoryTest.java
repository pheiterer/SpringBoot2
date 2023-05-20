package springboot2.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springboot2.domain.Anime;


@DataJpaTest
@DisplayName("Test for Anime Repository")
class AnimeRepositoryTest{
    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save creates Anime when successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(anime.getName());

    }


    private Anime createAnime(){
        return Anime.builder()
                .name("Hell's Paradise")
                .build();
    }
}