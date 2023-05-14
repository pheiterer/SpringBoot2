package springboot2.service;

import org.springframework.stereotype.Service;
import springboot2.domain.Anime;

import java.util.List;

@Service
public class AnimeService {
//    private final AnimeRepository animeRepository;
    public List<Anime> listAll(){
        return List.of(new Anime(1L,"Naruto"), new Anime(2L,"Berserker"));
    }
}
