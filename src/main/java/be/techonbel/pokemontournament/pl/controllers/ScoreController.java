package be.techonbel.pokemontournament.pl.controllers;

import be.techonbel.pokemontournament.bl.services.ScoreService;
import be.techonbel.pokemontournament.dal.models.entities.Score;
import be.techonbel.pokemontournament.pl.dtos.ScoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController {

    private ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    @PreAuthorize("isAnonymous()")
    @GetMapping("/all")
    public ResponseEntity<List<ScoreDTO>> getAll(){

        List<Score> scores = scoreService.getAll();
        List<ScoreDTO> dtos = scores.stream().map(ScoreDTO::fromEntity).toList();

        return ResponseEntity.ok(dtos);

    }
}
