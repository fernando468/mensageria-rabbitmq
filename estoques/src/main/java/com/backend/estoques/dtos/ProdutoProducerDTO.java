package com.backend.estoques.dtos;

import java.math.BigDecimal;

public record ProdutoProducerDTO(
        Long id,
        String nome,
        BigDecimal preco
) {
}
