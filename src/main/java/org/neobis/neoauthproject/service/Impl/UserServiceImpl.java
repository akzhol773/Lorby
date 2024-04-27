package org.neobis.neoauthproject.service.Impl;

import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.dto.UserAuthorizationRequestDto;
import org.neobis.neoauthproject.dto.UserAuthorizationResponseDto;
import org.neobis.neoauthproject.exception.EmailAlreadyExistException;
import org.neobis.neoauthproject.repository.UserRepository;
import org.neobis.neoauthproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto) {
        if (userRepository.findByEmail(registrationUserDto.email()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exist. Please, try to use another one.");
        }
    }
}
