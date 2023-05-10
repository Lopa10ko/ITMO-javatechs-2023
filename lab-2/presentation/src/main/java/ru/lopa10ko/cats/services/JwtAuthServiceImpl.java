package ru.lopa10ko.cats.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.lopa10ko.cats.controllers.requests.JwtCreateCatOwnerRequest;
import ru.lopa10ko.cats.controllers.requests.JwtRequest;
import ru.lopa10ko.cats.controllers.response.JwtResponse;
import ru.lopa10ko.cats.dao.CatOwnerRepository;
import ru.lopa10ko.cats.dao.UserRepository;
import ru.lopa10ko.cats.entities.CatOwner;
import ru.lopa10ko.cats.entities.User;
import ru.lopa10ko.cats.security.model.UserDto;
import ru.lopa10ko.cats.security.services.JwtService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtAuthServiceImpl implements JwtAuthService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CatOwnerRepository catOwnerRepository;

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
        var catOwner = new CatOwner(jwtCreateCatOwnerRequest.getLogin(), jwtCreateCatOwnerRequest.getBirthDate());
        catOwnerRepository.save(catOwner);
        var user = User.builder()
                .username(jwtCreateCatOwnerRequest.getLogin())
                .roles(Set.of(jwtCreateCatOwnerRequest.getDefaultRole()))
                .password(passwordEncoder.encode(jwtCreateCatOwnerRequest.getPassword()))
                .catOwner(catOwner)
                .build();
        userRepository.save(user);
        return JwtResponse.builder()
                .token(jwtService.generateToken(getUserDtoFromUser(user)))
                .build();
    }

    private UserDto getUserDtoFromUser(User user) {
        return UserDto.builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .catOwnerUuid(user.getCatOwner().getUuid())
                .roles(user.getRoles())
                .build();
    }
}
