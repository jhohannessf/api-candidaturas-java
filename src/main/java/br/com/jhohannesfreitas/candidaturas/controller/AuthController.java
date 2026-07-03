package br.com.jhohannesfreitas.candidaturas.controller;

import br.com.jhohannesfreitas.candidaturas.dto.LoginRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.RegisterRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.TokenResponseDTO;
import br.com.jhohannesfreitas.candidaturas.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação para novos usuários.")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void register(@RequestBody @Valid  RegisterRequestDTO registerRequestDTO ) throws BadRequestException {
        authenticationService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO ) throws BadRequestException {
        return authenticationService.login(loginRequestDTO);
    }

}
