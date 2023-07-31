package com.example.RednGreenBE.init;

import com.example.RednGreenBE.model.dto.request.AddressDataDTO;
import com.example.RednGreenBE.model.dto.request.GamePublishDTO;
import com.example.RednGreenBE.model.dto.request.RegistrationDTO;
import com.example.RednGreenBE.model.entities.UserEntity;
import com.example.RednGreenBE.repositories.GameRepository;
import com.example.RednGreenBE.repositories.UserRepository;
import com.example.RednGreenBE.service.GameService;
import com.example.RednGreenBE.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@AllArgsConstructor
public class DBInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final GameService gameService;
    private final PasswordEncoder passwordEncoder;



    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            RegistrationDTO placeholderData = createWithPlaceholderData();
            userService.registerUser(placeholderData);
            if (gameRepository.count() == 0) {
                GamePublishDTO superMeatBoy = createSuperMeatBoy();
                GamePublishDTO deltarune = createDeltarune();
                GamePublishDTO flappyBird = createFlappyBird();
                gameService.publishGame(superMeatBoy, placeholderData.getUsername());
                gameService.publishGame(deltarune, placeholderData.getUsername());
                gameService.publishGame(flappyBird, placeholderData.getUsername());
            }
        }
    }
    private RegistrationDTO createWithPlaceholderData() {
        RegistrationDTO registrationDTO = new RegistrationDTO();

        registrationDTO.setUsername("user");
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john.doe@example.com");
        registrationDTO.setPassword(passwordEncoder.encode("Password123"));
        registrationDTO.setPhoneNumber("+359123456789");
        registrationDTO.setMoney(BigDecimal.valueOf(1000));
        registrationDTO.setAddressData(new AddressDataDTO("Bulgaria", "Sofia", "Nekade"));

        return registrationDTO;
    }

    public static GamePublishDTO createSuperMeatBoy() {
        return new GamePublishDTO(
                "Super Meat Boy",
                "https://upload.wikimedia.org/wikipedia/en/a/aa/SuperMeatBoy_cover.png",
                "Deltarune is a role-playing video game created by Toby Fox, the creator of Undertale. In Deltarune, players embark on a thrilling adventure in a mysterious, dark world filled with fascinating characters, puzzles, and unexpected twists.\n\nFollow the story of Kris, a human who finds themselves in a strange and otherworldly realm known as the Dark World. Join forces with a group of quirky characters, including Susie and Ralsei, and work together to uncover the secrets of this peculiar world.\n\nAs you progress through the game, your choices will impact the outcome of the story and the relationships between the characters. Will you befriend your companions or confront them?\n\nDeltarune features an engaging turn-based battle system, where players can choose to fight, act, defend, or use special abilities to overcome challenging enemies. Solve intricate puzzles and explore every nook and cranny of the Dark World to unlock hidden secrets and discover the true nature of your journey.\n\nWith its unique art style, captivating storytelling, and memorable soundtrack, Deltarune offers an unforgettable gaming experience that will leave you pondering long after the final credits roll.\n\nDive into the enchanting world of Deltarune and embark on a journey that will test your wits, challenge your beliefs, and warm your heart. Unravel the mysteries of the Dark World and embrace the adventure that awaits!",
                BigDecimal.valueOf(14.99)
        );
    }

    public static GamePublishDTO createDeltarune() {
        return new GamePublishDTO(
                "Deltarune",
                "https://upload.wikimedia.org/wikipedia/en/0/0b/DeltaruneCombatScreenshot.png",
                "Deltarune is an enchanting and mysterious role-playing adventure created by Toby Fox, the developer behind the acclaimed Undertale.\n\nStep into the shoes of Kris, a human living in a town inhabited by monsters, as they embark on a journey with their new friends Susie and Ralsei to save the world from impending darkness.\n\nExplore the fascinating Dark World, solve challenging puzzles, and engage in turn-based battles where your choices matter. Your decisions will shape the outcome of the story and the fate of the characters.\n\nWith its heartwarming characters, engaging narrative, and masterfully composed soundtrack, Deltarune is an experience that will tug at your heartstrings and leave you pondering its mysteries long after the adventure ends.\n\nImmerse yourself in a captivating world of magic, friendship, and choices as you unveil the secrets of Deltarune and discover what lies beyond the darkness.\n\nEmbark on a heartfelt journey filled with humor, surprises, and touching moments that will leave a lasting impression on players of all ages.\n\nUnravel the threads of fate, embrace the unknown, and prepare for an unforgettable journey into the world of Deltarune.",
                BigDecimal.valueOf(19.99)
        );
    }

    public static GamePublishDTO createFlappyBird() {
        return new GamePublishDTO(
                "Flappy Bird",
                "https://upload.wikimedia.org/wikipedia/en/5/52/Flappy_Bird_gameplay.png",
                "Flap yo$r wings and fly through an endless series of pipes in Flappy Bird, the wildly addictive mobile game that took the world by storm.\n\nThe concept is simple: tap the screen to keep the cute bird afloat and avoid the green pipes. Easy to pick up but incredibly challenging to master, Flappy Bird tests your reflexes and patience to the max.\n\nWith its minimalist graphics, straightforward gameplay, and addictive nature, Flappy Bird quickly became a cultural phenomenon and a symbol of mobile gaming in the early 2010s.\n\nCompete with friends and players from around the world to achieve the highest score and become the ultimate Flappy Bird champion.\n\nDo you have what it takes to navigate through the narrow gaps and achieve an impressive score? Or will you fall victim to the unforgiving pipes and flap your way to frustration?\n\nFlappy Bird is the perfect game for quick, casual gaming sessions or intense challenges to beat your personal best.\n\nJoin the millions of players who have experienced the addictive gameplay and simple joy of Flappy Bird and see how far you can go in this classic mobile game.",
                BigDecimal.valueOf(0.99)
        );
    }
}