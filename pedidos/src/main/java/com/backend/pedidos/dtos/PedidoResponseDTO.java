package com.backend.pedidos.dtos;

import com.backend.pedidos.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        BigDecimal total,
        StatusPedidoEnum status,
        List<PedidoItemResponseDTO> listaPedidoItem
) {
}
