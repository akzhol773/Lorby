package org.neobis.neoauthproject.dto;

import lombok.Builder;

/**
 * DTO for {@link com.neobis.neoauth.entities.User}
 */

@Builder
public record UserAuthorizationResponseDto(String status, String username) {
}