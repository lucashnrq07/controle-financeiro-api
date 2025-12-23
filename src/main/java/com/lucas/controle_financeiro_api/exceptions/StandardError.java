package com.lucas.controle_financeiro_api.exceptions;

import java.time.Instant;

public record StandardError(
        Instant timestamp,
        int status,
        String error,
        String message
) {

    public static StandardError notFound(String message) {
        return new StandardError(
                Instant.now(),
                404,
                "Resource not found",
                message
        );
    }
}
