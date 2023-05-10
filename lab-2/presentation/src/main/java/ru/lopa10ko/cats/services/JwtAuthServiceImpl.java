package ru.lopa10ko.cats.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopa10ko.cats.controllers.requests.JwtRequest;
import ru.lopa10ko.cats.controllers.response.JwtResponse;
import ru.lopa10ko.cats.dao.UserRepository;
import ru.lopa10ko.cats.entities.User;
import ru.lopa10ko.cats.security.model.UserDto;
import ru.lopa10ko.cats.security.services.JwtService;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public JwtResponse authenticate(JwtRequest jwtRequest) {
        User user = userRepository.findByUsername(jwtRequest.getLogin())
                .orElseThrow(RuntimeException::new);
        var userDto = UserDto.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .catOwnerUuid(user.getCatOwner().getUuid())
                .roles(user.getRoles())
                .build();
        if (!passwordEncoder.matches(jwtRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException();
        }
        return JwtResponse.builder()
                .token(jwtService.generateToken(userDto))
                .build();
    }

    @Override
    public JwtResponse register(JwtRequest jwtRequest) {
        return null;
    }
}
