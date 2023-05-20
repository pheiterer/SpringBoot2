package springboot2.repository;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import springboot2.domain.Anime;

import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Test for Anime Repository")
class AnimeRepositoryTest{
    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Save persist Anime when successful")
    void save_PersistAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);

        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(anime.getName());

    }

    @Test
    @DisplayName("Save updates Anime when successful")
    void save_UpdatesAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);

        savedAnime.setName("Viland");
        Anime animeUpdated = this.animeRepository.save(savedAnime);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(savedAnime.getName());

    }

    @Test
    @DisplayName("Delete removes Anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);

        this.animeRepository.delete(savedAnime);

        Optional<Anime> animeOptional = this.animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(animeOptional).isEmpty();

    }

    @Test
    @DisplayName("Find by Name returns list of Anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
        Anime anime = createAnime();
        Anime savedAnime = this.animeRepository.save(anime);

        String name = savedAnime.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty().contains(savedAnime);

    }

    @Test
    @DisplayName("Find by Name returns empty list of Anime when no anime is found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        List<Anime> animes = this.animeRepository.findByName("ada");

        Assertions.assertThat(animes).isEmpty();

    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.animeRepository.save(anime)).withMessageContaining("The anime name cannot be empty");



    }


    private Anime createAnime(){
        return Anime.builder()
                .name("Hell's Paradise")
                .build();
    }
}