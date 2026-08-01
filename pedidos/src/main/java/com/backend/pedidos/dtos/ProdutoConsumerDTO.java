package com.backend.pedidos.dtos;

import java.math.BigDecimal;

public record ProdutoConsumerDTO(
        Long id,
        String nome,
        BigDecimal preco
) {
}
