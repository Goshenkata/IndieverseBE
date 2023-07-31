package com.example.RednGreenBE.service;

import com.example.RednGreenBE.model.dto.request.GamePublishDTO;
import com.example.RednGreenBE.model.dto.response.GameResponseDTO;
import com.example.RednGreenBE.model.entities.Game;
import com.example.RednGreenBE.model.entities.UserEntity;
import com.example.RednGreenBE.repositories.GameRepository;
import com.example.RednGreenBE.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transient
    public boolean publishGame(GamePublishDTO gameDTO, String author) {
        Game game = modelMapper.map(gameDTO, Game.class);
        Optional<UserEntity> user = userRepository.findByUsername(author);
        if (user.isEmpty()) return false;
        game.setAuthor(user.get());
        gameRepository.save(game);
        return true;
    }

    public List<GameResponseDTO> getPublished(String author) {
        UserEntity userEntity = userRepository.findByUsername(author).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        return userEntity.getPublishedGames()
                .stream()
                .map(game -> modelMapper.map(game, GameResponseDTO.class))
                .toList();


    }

    public GameResponseDTO getGame(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("No game with id " + id + " found."));
        return modelMapper.map(game, GameResponseDTO.class);
    }

    @Transactional
    public List<GameResponseDTO> getPopular() {
        return gameRepository.findGamesWithMostOwners()
                .stream()
                .map(game -> modelMapper.map(game, GameResponseDTO.class))
                .toList();
    }

    @Transactional
    public boolean buyGame(Long id, String name) {
        if (!hasEnoughMoney(id, name)) {
            return false;
        }
        Game game = gameRepository.findById(id).get();
        UserEntity userEntity = userRepository.findByUsername(name).get();

        if (game.getOwners().contains(userEntity)) return false;
        if (game.getAuthor().getUsername().equals(name)) return false;

        game.getOwners().add(userEntity);
        userEntity.setMoney(userEntity.getMoney().subtract(game.getPrice()));
        userRepository.save(userEntity);
        gameRepository.save(game);
        return true;
    }

    @Transactional
    public boolean deleteGame(Long id, String name) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        Optional<UserEntity> byUsername = userRepository.findByUsername(name);
        if (gameOptional.isEmpty()) return false;
        if (byUsername.isEmpty()) return false;
        Game game = gameOptional.get();
        UserEntity userEntity = byUsername.get();
        if (!game.getAuthor().getUsername().equals(name)) return false;
        userEntity.getPublishedGames().remove(game);
        gameRepository.delete(game);
        return true;
    }

    public Boolean ownsGame(Long id, String name) {
        Optional<Game> byId = gameRepository.findById(id);
        Optional<UserEntity> byUsername = userRepository.findByUsername(name);
        if (byId.isEmpty()) return false;
        if (byUsername.isEmpty()) return false;
        Game game = byId.get();
        UserEntity userEntity = byUsername.get();
        return game.getOwners().contains(userEntity);

    }

    public boolean gameExists(String name) {
        return gameRepository.existsByName(name);
    }

    public boolean hasEnoughMoney(Long id, String name) {
        Optional<Game> byId = gameRepository.findById(id);
        Optional<UserEntity> byUsername = userRepository.findByUsername(name);
        if (byId.isEmpty()) return false;
        if (byUsername.isEmpty()) return false;
        Game game = byId.get();
        UserEntity userEntity = byUsername.get();
        return userEntity.getMoney().compareTo(game.getPrice()) >= 0;
    }

    public List<GameResponseDTO> getGames(String name) {
        UserEntity userEntity = userRepository.findByUsername(name).get();
        return userEntity.getOwnedGames()
                .stream()
                .map(g -> modelMapper.map(g, GameResponseDTO.class))
                .toList();
    }
}
