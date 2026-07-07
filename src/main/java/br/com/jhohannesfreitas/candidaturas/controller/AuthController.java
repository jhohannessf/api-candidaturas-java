package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.LoginRequest;
import br.com.jhohannesfreitas.candidaturas.dto.RegisterRequest;
import br.com.jhohannesfreitas.candidaturas.dto.TokenResponse;
import br.com.jhohannesfreitas.candidaturas.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação para novos usuários.")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerRequest) throws BadRequestException {
        authenticationService.register(registerRequest);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) throws BadRequestException {
        return authenticationService.login(loginRequest);
    }

}
