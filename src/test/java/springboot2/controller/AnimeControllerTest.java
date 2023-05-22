package springboot2.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import springboot2.domain.Anime;
import springboot2.requests.AnimePostRequestBody;
import springboot2.requests.AnimePutRequestBody;
import springboot2.service.AnimeService;
import springboot2.util.AnimeCreator;
import springboot2.util.AnimePostRequesBodyCreator;
import springboot2.util.AnimePutRequesBodyCreator;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeService;

    @BeforeEach
    void setup() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeService.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeService.listAllNoPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeService.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeService.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeService).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeService).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("List Returns List Of Animes Inside Page Object When Successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        Page<Anime> anime = animeController.list(null).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.toList()).isNotEmpty();
        Assertions.assertThat(anime.toList().get(0).getName()).isEqualTo(name);
        Assertions.assertThat(anime.toList()).isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("ListAll Returns List Of Animes Object When Successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeController.listAll().getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("FindById Returns Anime Object When Successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long id = AnimeCreator.createValidAnime().getId();
        Anime anime = animeController.findById(2).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(id);
    }

    @Test
    @DisplayName("FindByName Returns Anime Object When Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        String name = AnimeCreator.createValidAnime().getName();
        List<Anime> anime = animeController.findByName("").getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(anime.get(0).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("FindByName Returns an empty list of anime When anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
        BDDMockito.when(animeService.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> anime = animeController.findByName("").getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("Save Returns Anime Object When Successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Long id = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.save(AnimePostRequesBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("Replace update Anime Object When Successful")
    void Replace_UpdateAnime_WhenSuccessful() {

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequesBodyCreator.createAnimePouRequestBody());

        Assertions.assertThatCode(() ->animeController.replace(AnimePutRequesBodyCreator.createAnimePouRequestBody()).getBody()).doesNotThrowAnyException();
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("Delete update Anime Object When Successful")
    void Delete_RemovesAnime_WhenSuccessful() {

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertThatCode(() ->animeController.delete(1)).doesNotThrowAnyException();
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}