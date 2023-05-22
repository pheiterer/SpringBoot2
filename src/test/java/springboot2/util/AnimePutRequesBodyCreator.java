package springboot2.util;

import springboot2.requests.AnimePutRequestBody;

public class AnimePutRequesBodyCreator {
    public static AnimePutRequestBody createAnimePouRequestBody(){
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidUpdateAnime().getId())
                .name(AnimeCreator.createValidUpdateAnime().getName())
                .build();
    }
}
