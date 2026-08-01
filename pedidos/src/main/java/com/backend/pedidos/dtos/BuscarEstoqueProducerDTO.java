package com.backend.pedidos.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record BuscarEstoqueProducerDTO(
        UUID id,
        LocalDateTime localDateTime
) {
}
