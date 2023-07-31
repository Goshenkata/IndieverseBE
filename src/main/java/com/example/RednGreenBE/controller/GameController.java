package com.example.RednGreenBE.controller;


import com.example.RednGreenBE.model.dto.request.GamePublishDTO;
import com.example.RednGreenBE.model.dto.request.SearchDTO;
import com.example.RednGreenBE.model.dto.response.GameResponseDTO;
import com.example.RednGreenBE.model.dto.response.SimpleMessageDTO;
import com.example.RednGreenBE.service.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/games")
public class GameController  {
    private final GameService gameService;
    @PostMapping("/create")
    public ResponseEntity<SimpleMessageDTO> createGame(@Valid @RequestBody GamePublishDTO gameDTO, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    new SimpleMessageDTO(bindingResult.getAllErrors().get(0).getDefaultMessage()));
        }
        if (gameService.gameExists(gameDTO.getName())) {
            return ResponseEntity.badRequest().body(new SimpleMessageDTO("Game already exists"));
        }
        if(gameService.publishGame(gameDTO, principal.getName())) {
            return ResponseEntity.ok(new SimpleMessageDTO("Game created successfully"));
        }
        return ResponseEntity.internalServerError().body(new SimpleMessageDTO("Something went wrong"));
    }

    @GetMapping("/getPublished")
    public ResponseEntity<List<GameResponseDTO>> getPublished(Principal principal) {
        return ResponseEntity.ok(gameService.getPublished(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGame(id));
    }

    @GetMapping("/popular")
    private ResponseEntity<List<GameResponseDTO>> getMostPopularGames() {
        return ResponseEntity.ok(gameService.getPopular());
    }

    @PostMapping("/buy/{id}")
    private ResponseEntity<SimpleMessageDTO> buyGame(@PathVariable Long id, Principal principal) {
        if (!gameService.hasEnoughMoney(id, principal.getName())) {
            return ResponseEntity.badRequest().body(new SimpleMessageDTO("Not enough money!"));
        }
        if (gameService.buyGame(id, principal.getName())) {
            return ResponseEntity.ok(new SimpleMessageDTO("Game bought succesfully"));
        } else {
            return ResponseEntity.badRequest().body(new SimpleMessageDTO("Something went wrong!"));
        }
    }
    @DeleteMapping("/{id}")
    private ResponseEntity<SimpleMessageDTO> deleteGame(@PathVariable Long id, Principal principal) {
        if (gameService.deleteGame(id, principal.getName())) {
            return ResponseEntity.ok(new SimpleMessageDTO("Game deleted successfully"));
        } else {
            return ResponseEntity.badRequest().body(new SimpleMessageDTO("Something went wrong deleting the game"));
        }
    }

    @GetMapping("/owns/{id}")
    ResponseEntity<Boolean> ownsGame(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(gameService.ownsGame(id, principal.getName()));
    }

    @GetMapping("/mygames")
    ResponseEntity<List<GameResponseDTO>> mygames(Principal principal) {
        return ResponseEntity.ok(gameService.getGames(principal.getName()));
    }

    @GetMapping("/user/{username}")
    ResponseEntity<List<GameResponseDTO>> getGamesForUser(@PathVariable String username) {
        return ResponseEntity.ok(gameService.getPublished(username));
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@Valid @RequestBody SearchDTO searchDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(gameService.search(searchDTO));
    }
}
