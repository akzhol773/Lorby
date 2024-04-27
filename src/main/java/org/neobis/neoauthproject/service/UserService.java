package org.neobis.neoauthproject.service;

import org.neobis.neoauthproject.dto.UserAuthorizationRequestDto;
import org.neobis.neoauthproject.dto.UserAuthorizationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserService {
    ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto);
}
