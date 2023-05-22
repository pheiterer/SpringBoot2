package springboot2.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot2.domain.Anime;
import springboot2.exception.BadRequestException;
import springboot2.repository.AnimeRepository;
import springboot2.util.AnimeCreator;
import springboot2.util.AnimePostRequesBodyCreator;
import springboot2.util.AnimePutRequesBodyCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;

    @BeforeEach
    void setup() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepository.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepository.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());


        BDDMockito.doNothing().when(animeRepository).delete(ArgumentMatchers.any(Anime.class));

    }

    @Test
    @DisplayName("ListAll Returns List Of Animes Inside Page Object When Successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        Page<Anime> anime = animeService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.toList()).isNotEmpty();
        Assertions.assertThat(anime.toList().get(0).getName()).isEqualTo(name);
        Assertions.assertThat(anime.toList()).isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("ListAllNoPageable Returns List Of Animes Object When Successful")
    void listAllNoPageable_ReturnsListOfAnimes_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeService.listAllNoPageable();

        Assertions.assertThat(anime)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("FindByIdOrThrowBadRequestException Returns Anime Object When Successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful() {
        Long id = AnimeCreator.createValidAnime().getId();
        Anime anime = animeService.findByIdOrThrowBadRequestException(2);

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("FindByIdOrThrowBadRequestException Throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());


        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(2));

    }

    @Test
    @DisplayName("FindByName Returns Anime Object When Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeService.findByName("");

        Assertions.assertThat(anime)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("FindByName Returns an empty list of anime When anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> anime = animeService.findByName("");

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("Save Returns Anime Object When Successful")
    void save_ReturnsAnime_WhenSuccessful() {

        Anime anime = animeService.save(AnimePostRequesBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace update Anime Object When Successful")
    void Replace_UpdateAnime_WhenSuccessful() {

        Assertions.assertThatCode(() ->animeService.replace(AnimePutRequesBodyCreator.createAnimePouRequestBody())).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Delete update Anime Object When Successful")
    void Delete_RemovesAnime_WhenSuccessful() {


        Assertions.assertThatCode(() ->animeService.delete(1)).doesNotThrowAnyException();

    }

}