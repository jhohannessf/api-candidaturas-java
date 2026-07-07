package br.com.jhohannesfreitas.candidaturas.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String message,
        int status,
        LocalDateTime timestamp,
        String error

) {
}

