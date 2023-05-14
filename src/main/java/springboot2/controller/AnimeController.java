package springboot2.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot2.domain.Anime;
import springboot2.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("anime")
@Log4j2
@AllArgsConstructor
public class AnimeController {
    private final DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Anime> list() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Anime("Naruto"), new Anime("Berserker"));
    }

}
