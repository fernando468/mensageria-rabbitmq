package com.backend.pedidos.dtos;

import java.util.List;

public record CriarPedidoRequestDTO(
        List<PeditoItemRequestDTO> listaPedidoItem
) {
}
