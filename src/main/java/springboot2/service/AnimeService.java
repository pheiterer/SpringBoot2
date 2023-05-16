package springboot2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springboot2.domain.Anime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AnimeService {
    public static List<Anime> animes;

    static {
        animes = new ArrayList<>(List.of(new Anime(1L, "Naruto"), new Anime(2L, "Berserker")));
    }

    //    private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return animes;
    }


    public Anime findById(long id){
        return animes.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Anime not Found"));
    }

    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3,100));
        animes.add(anime);
        return anime;
    }
}
