package com.backend.pedidos.dtos;

public record PeditoItemRequestDTO(
        Long idProduto,
        Long quantidade
) {
}
