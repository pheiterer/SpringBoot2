package springboot2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springboot2.domain.Anime;

import java.util.List;

@Service
public class AnimeService {
    List<Anime> animes = List.of(new Anime(1L, "Naruto"), new Anime(2L, "Berserker"));

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
}
