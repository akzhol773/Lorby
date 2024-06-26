package org.neobis.neoauthproject.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.dto.*;
import org.neobis.neoauthproject.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth/")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Registration",
            description = "Endpoint for customer to register a new account. Requires a body"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "The provided username is already taken"),
            @ApiResponse(responseCode = "409", description = "The provided email is already taken")
    })

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserAuthorizationResponseDto> register(@RequestBody UserAuthorizationRequestDto registrationUserDto){
        return  authService.createNewUser(registrationUserDto);}



    @Operation(
            summary = "Login",
            description = "Endpoint for getting tokens after login"

    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned a token"),
            @ApiResponse(responseCode = "403", description = "Username or password is invalid"),
            @ApiResponse(responseCode = "403", description = "Username is not enabled")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto authRequest){
        return  authService.authenticate(authRequest);

    }

    @Operation(
            summary = "Check username",
            description = "This endpoint is designed to check if the username exists in the database or not",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns true or false")
            }
    )
    @GetMapping("/check-username")
    public Boolean checkUsername(@RequestBody UsernameDto dto) {
        return authService.isPresentUsername(dto);
    }

    @Operation(
            summary = "Confirm the email",
            description = "Whenever a user is registered he or she gets email containing link to activate his or her account"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully confirmed"),
            @ApiResponse(responseCode = "403", description = "Token has expired"),
            @ApiResponse(responseCode = "403", description = "Token not found")


    })
    @Hidden
    @GetMapping("/confirm-email")
    public RedirectView confirm(@RequestParam("token") String token) {
        authService.confirmEmail(token);
        return new RedirectView("https://jazzy-chimera-5f8327.netlify.app/confirmed");
    }


    @Operation(
            summary = "Refresh the token",
            description = "If the token is expired then it is possible to generate a new access token using refresh token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully returned a new access token"),
            @ApiResponse(responseCode = "403", description = "Token has expired"),
            @ApiResponse(responseCode = "403", description = "Token not found"),

    })

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtRefreshTokenDto> refreshToken(@RequestParam("refreshToken") String refreshToken){
        return  authService.refreshToken(refreshToken);

    }

    @Operation(
            summary = "Resend confirmation email",
            description = "User can get another link to confirm their email"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully confirmed"),
            @ApiResponse(responseCode = "403", description = "Token has expired"),
            @ApiResponse(responseCode = "403", description = "Token not found")

    })
    @PostMapping("/resend-email")
    public ResponseEntity<String> resendEmail(@RequestBody ResendEmailDto dto) {
        return  authService.resendConfirmationEmail(dto);
    }

    @Operation(
            summary = "Users can reset their password using this link. It sends link to the email",
            description = "User can get another link to confirm their email"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Link has been sent to the email"),
            @ApiResponse(responseCode = "403", description = "User not found"),

    })
    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto dto){
        return authService.forgotPassword(dto);
    }

    @Operation(
            summary = "Reset password",
            description = "Resetting the password with the provided new password"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password has been changed successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token"),

    })
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam ("resetToken") String resetToken, @RequestBody ResetPasswordDto resetPasswordDto){
        return authService.resetPassword(resetToken, resetPasswordDto);
    }



}
