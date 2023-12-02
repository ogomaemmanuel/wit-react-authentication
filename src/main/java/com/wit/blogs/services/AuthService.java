package com.wit.blogs.services;

import com.wit.blogs.config.JwtSecretProperties;
import com.wit.blogs.dto.LoginRequestDto;
import com.wit.blogs.dto.LoginResponse;
import com.wit.blogs.dto.UserRegistrationDto;
import com.wit.blogs.entities.UserEntity;
import com.wit.blogs.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtSecretProperties jwtSecretProperties;
    private final PasswordEncoder passwordEncoder;


    public UserEntity registerUser(UserRegistrationDto userRegistrationDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userRegistrationDto.getEmail());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setPassword(userRegistrationDto.getPassword());
        return userRepository.save(user);
    }



    public LoginResponse authenticate(LoginRequestDto loginRequest) throws Exception {
        Optional<UserEntity> user = this.userRepository.findUserEntityByEmailIgnoreCase(loginRequest.getUsername());
        return user.filter(userEntity -> {
            if (passwordEncoder.matches(loginRequest.getPassword(), userEntity.getPassword())) {
                return true;
            }
            throw new RuntimeException("Invalid username/password combination");
        }).map(userEntity -> {
            HashMap<String, Object> claims= new HashMap<>();
            claims.put("userId",userEntity.getId());
            String accessToken = generateJwtToken(jwtSecretProperties.getAccessTokenSecret(), userEntity.getUsername(),
                   claims, Date.from(Instant.now().plusSeconds(1_800)));
            String refreshToken = generateJwtToken(jwtSecretProperties.getRefreshTokenSecret(), userEntity.getUsername(),
                   claims, Date.from(Instant.now().plusSeconds(3_600 * 24)));
            LoginResponse loginResponse = new LoginResponse(accessToken,refreshToken,1_800*1000l);
            return loginResponse;
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    public String generateJwtToken(String secret, String username, Date expirationTime) {
        return generateJwtToken(secret, username, new HashMap<>(), expirationTime);
    }

    public String generateJwtToken(String secret, String username, HashMap<String, Object> claims, Date expirationTime) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        var accessToken = Jwts.builder().subject(username)
                .expiration(expirationTime)
                .claims().add(claims)
                .and()
                .signWith(key)
                .compact();
        return accessToken;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return this.userRepository.findUserEntityByEmailIgnoreCase(username)
                .orElseThrow(()->new UsernameNotFoundException("User " + username + " does not exist"));
    }
}
