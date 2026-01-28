package com.lucas.controle_financeiro_api.dto.login;

import java.time.Instant;
import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        int status,
        String message
) {}