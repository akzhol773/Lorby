package org.neobis.neoauthproject.service;

import org.neobis.neoauthproject.dto.*;
import org.neobis.neoauthproject.entity.ConfirmationToken;
import org.neobis.neoauthproject.entity.User;
import org.springframework.http.ResponseEntity;


public interface AuthService {
    ResponseEntity<UserAuthorizationResponseDto> createNewUser(UserAuthorizationRequestDto registrationUserDto);

    boolean isPresentUsername(UsernameDto dto);

    ConfirmationToken generateConfirmToken(User user);

    void confirmEmail(String token);

    ResponseEntity<JwtResponseDto> authenticate(JwtRequestDto authRequest);

    ResponseEntity<JwtRefreshTokenDto> refreshToken(String refreshToken);

    ResponseEntity<String> resendConfirmation(ResendEmailDto dto);

    ResponseEntity<String> forgotPassword(ForgotPasswordDto dto);

    ResponseEntity<String> resetPassword(String resetToken, ResetPasswordDto resetPasswordDto);
}
