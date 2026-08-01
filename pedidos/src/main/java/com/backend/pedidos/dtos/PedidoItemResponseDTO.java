package com.backend.pedidos.dtos;

import java.math.BigDecimal;

public record PedidoItemResponseDTO(
        Long quantidade,
        BigDecimal preco,
        BigDecimal total
) {
}
