package springboot2.util;

import springboot2.requests.AnimePostRequestBody;

public class AnimePostRequesBodyCreator {
    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
