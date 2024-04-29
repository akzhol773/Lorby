package org.neobis.neoauthproject.dto;


import lombok.Builder;


@Builder
public record JwtResponseDto(String username, String accessToken, String refreshToken){
}