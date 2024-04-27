package org.neobis.neoauthproject.dto;


import lombok.Builder;

@Builder
public record UserAuthorizationRequestDto(
        String email,
        String username,
        String password,
        String confirmPassword) {

}