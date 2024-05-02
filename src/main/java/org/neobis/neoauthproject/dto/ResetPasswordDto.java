package org.neobis.neoauthproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordDto(
        @NotNull(message = "Password field should not be null")
        @NotBlank(message = "Password is required")
        String newPassword,
        String confirmNewPassword) {
}
