package org.neobis.neoauthproject.service;

import org.neobis.neoauthproject.dto.UserAuthorizationRequestDto;
import org.neobis.neoauthproject.dto.UserAuthorizationResponseDto;
import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.entity.User;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto);

    boolean isPresentUsername(String username);

    ConfirmationToken generateConfirmToken(User user);
}
