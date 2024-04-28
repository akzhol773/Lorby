package org.neobis.neoauthproject.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.dto.UserAuthorizationRequestDto;
import org.neobis.neoauthproject.dto.UserAuthorizationResponseDto;
import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.entity.Role;
import org.neobis.neoauthproject.entity.User;
import org.neobis.neoauthproject.exception.*;
import org.neobis.neoauthproject.repository.RoleRepository;
import org.neobis.neoauthproject.repository.UserRepository;
import org.neobis.neoauthproject.service.AuthService;
import org.neobis.neoauthproject.service.ConfirmationTokenService;
import org.neobis.neoauthproject.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private static final String CONFIRM_EMAIL_LINK = System.getenv("CONFIRM_EMAIL_LINK");
    @Override
    @Transactional
    public ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto) {
        if (userRepository.findByEmailIgnoreCase(registrationUserDto.email()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exist. Please, try to use another one.");
        }
        if (userRepository.findByUsernameIgnoreCase(registrationUserDto.username()).isPresent()) {
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
    public boolean isPresentUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username).isPresent();
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
    public String confirmEmail(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(()->new TokenNotFoundException("Token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Token has expired");

        }

        if (confirmationToken != null){
            confirmationToken.setConfirmedAt(LocalDateTime.now());
            confirmationToken.getUser().setEnabled(true);
            return "Email confirmed successfully. Go to the login page.";
        }else {
            return "verification_failed";
        }
    }

}
