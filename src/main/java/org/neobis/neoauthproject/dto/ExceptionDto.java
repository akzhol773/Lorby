package org.neobis.neoauthproject.dto;

import lombok.Builder;

@Builder
public record ExceptionDto(
        String time,
        String message
) {
}
