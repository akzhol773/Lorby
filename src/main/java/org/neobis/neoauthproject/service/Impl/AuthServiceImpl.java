package org.neobis.neoauthproject.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.component.JwtTokenUtils;
import org.neobis.neoauthproject.dto.*;
import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.entity.Role;
import org.neobis.neoauthproject.entity.User;
import org.neobis.neoauthproject.exception.*;
import org.neobis.neoauthproject.repository.ConfirmationTokenRepository;
import org.neobis.neoauthproject.repository.RoleRepository;
import org.neobis.neoauthproject.repository.UserRepository;
import org.neobis.neoauthproject.service.AuthService;
import org.neobis.neoauthproject.service.ConfirmationTokenService;
import org.neobis.neoauthproject.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private static final String CONFIRM_EMAIL_LINK = System.getenv("CONFIRM_EMAIL_LINK");
    @Override
    @Transactional
    public ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto) {
        if (userRepository.findByEmailIgnoreCase(registrationUserDto.email()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exist. Please, try to use another one.");
        }
        if (userRepository.findByUsername(registrationUserDto.username()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken. Please, try to use another one.");
        }
        User user = new User();
        user.setEnabled(false);
        user.setEmail(registrationUserDto.email());
        user.setUsername(registrationUserDto.username());
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new UserRoleNotFoundException("User role not found."));
        user.setRoles(Collections.singletonList(userRole));
        String password = registrationUserDto.password();
        String confirmPassword = registrationUserDto.confirmPassword();
        if (!password.equals(confirmPassword)) {
            throw new PasswordNotMatchException("Passwords do not match.");
        }
        user.setPassword(passwordEncoder.encode(registrationUserDto.password()));
        userRepository.save(user);
        ConfirmationToken confirmationToken = generateConfirmToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String link = CONFIRM_EMAIL_LINK + confirmationToken.getToken();
        emailService.prepareMail(link, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new UserAuthorizationResponseDto(user.getUsername(),"User registered successfully"));

    }


    @Override
    public boolean isPresentUsername(UsernameDto dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            return true;
        }else {
            return false;
        }
    }


    @Override
    public ConfirmationToken generateConfirmToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
                confirmationToken.setToken(token);
                confirmationToken.setCreatedAt(LocalDateTime.now());
                confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(5));
                confirmationToken.setConfirmedAt(null);
                confirmationToken.setUser(user);
        return confirmationToken;
    }

    @Override
    public void confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("Token has expired");
        }

        User user = confirmationToken.getUser();
        if (user != null) {
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationTokenRepository.save(confirmationToken);
            user.setEnabled(true);
            userRepository.save(user);
        }else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Override
    public ResponseEntity<JwtResponseDto> authenticate(JwtRequestDto authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            User user = (User) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponseDto(authRequest.username(),
                    jwtTokenUtils.generateAccessToken(user),
                    jwtTokenUtils.generateRefreshToken(user)));


        } catch (AuthenticationException exception) {
            if (exception instanceof BadCredentialsException) {
                throw new BadCredentialsException("Invalid username or password");
            } else {
                throw new DisabledException("User is not enabled yet");
            }
        }
    }

    @Override
    public ResponseEntity<JwtRefreshTokenDto> refreshToken(String refreshToken) {
        try {
            if (refreshToken == null) {
                return ResponseEntity.badRequest().build();
            }

            String usernameFromRefreshToken = jwtTokenUtils.getUsernameFromRefreshToken(refreshToken);
            if (usernameFromRefreshToken == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            User user = userRepository.findByUsername(usernameFromRefreshToken).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));


            String accessToken = jwtTokenUtils.generateAccessToken(user);
            return ResponseEntity.ok(new JwtRefreshTokenDto(usernameFromRefreshToken, accessToken));

        } catch (InvalidTokenException e) {
            throw new InvalidTokenException("Given token is invalid");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Username not found");
        } catch (Exception e) {
            throw new ServerErrorException("There occurred an error.", e.getCause());
        }
    }

    @Override
    public ResponseEntity<String> resendConfirmation(ResendEmailDto dto) {
        User user = userRepository.findByEmailIgnoreCase(dto.email()).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        if(user.isEnabled()){
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        List<ConfirmationToken> confirmationTokens = confirmationTokenRepository.findByUser(user);
        for(ConfirmationToken confirmationToken : confirmationTokens){
            confirmationToken.setToken(null);
            confirmationTokenRepository.save(confirmationToken);
        }


        ConfirmationToken newConfirmationToken = generateConfirmToken(user);
        confirmationTokenRepository.save(newConfirmationToken);
        String link = CONFIRM_EMAIL_LINK + newConfirmationToken.getToken();
        emailService.prepareMail(link, user);
        return ResponseEntity.ok("Success! Please, check your email for the re-confirmation");
    }

}
