package ru.lopa10ko.dal.services;

import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopa10ko.dal.controllers.requests.JwtCreateCatOwnerRequest;
import ru.lopa10ko.dal.controllers.requests.JwtRequest;
import ru.lopa10ko.dal.controllers.response.JwtResponse;
import ru.lopa10ko.dal.entity.User;
import ru.lopa10ko.dal.repository.UserRepository;
import ru.lopa10ko.dal.security.model.Roles;
import ru.lopa10ko.dal.security.model.UserDto;
import ru.lopa10ko.dal.security.services.JwtService;
import ru.lopa10ko.messaging.dto.constants.QueueName;
import ru.lopa10ko.messaging.dto.owner.OwnerCreateMessage;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public JwtResponse authenticate(JwtRequest jwtRequest) {
        User user = userRepository.findByUsername(jwtRequest.getLogin())
                .orElseThrow(RuntimeException::new);
        var userDto = getUserDtoFromUser(user);
        if (!passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }
        return JwtResponse.builder()
                .token(jwtService.generateToken(userDto))
                .build();
    }

    @Override
    public JwtResponse register(JwtCreateCatOwnerRequest jwtCreateCatOwnerRequest) {
        var catOwner = OwnerCreateMessage.builder()
            .uuid(UUID.randomUUID())
            .birthDay(jwtCreateCatOwnerRequest.getBirthDate())
            .name(jwtCreateCatOwnerRequest.getLogin())
            .build();
        var user = User.builder()
                .username(jwtCreateCatOwnerRequest.getLogin())
                .roles(Set.of(Roles.USER))
                .password(passwordEncoder.encode(jwtCreateCatOwnerRequest.getPassword()))
                .catOwnerId(catOwner.getUuid())
                .build();

        userRepository.save(user);
        rabbitTemplate.convertAndSend(QueueName.OWNER_CREATE, catOwner);

        return JwtResponse.builder()
                .token(jwtService.generateToken(getUserDtoFromUser(user)))
                .build();
    }

    private UserDto getUserDtoFromUser(User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .catOwnerUuid(user.getCatOwnerId())
                .roles(user.getRoles())
                .build();
    }
}
