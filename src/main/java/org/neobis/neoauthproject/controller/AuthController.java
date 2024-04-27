package org.neobis.neoauthproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.neobis.neoauthproject.dto.UserAuthorizationRequestDto;
import org.neobis.neoauthproject.dto.UserAuthorizationResponseDto;
import org.neobis.neoauthproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth/")
public class AuthController {

    private final UserService userService;

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
    public ResponseEntity<UserAuthorizationResponseDto> register(@Valid @RequestBody UserAuthorizationRequestDto registrationUserDto){
        return  userService.createNewUser(registrationUserDto);}


}
