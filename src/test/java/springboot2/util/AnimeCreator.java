package springboot2.util;

import springboot2.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved(){
        return Anime.builder()
                .name("Hell's Paradise")
                .build();
    }

    public static Anime createValidAnime(){
        return Anime.builder()
                .name("Hell's Paradise")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime(){
        return Anime.builder()
                .name("Hell's Paradise 2")
                .id(1L)
                .build();
    }
}
