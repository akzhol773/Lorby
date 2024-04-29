package org.neobis.neoauthproject.dto;

import lombok.Builder;

@Builder
public record JwtRefreshTokenDto(String username, String newAccessToken) {
}
