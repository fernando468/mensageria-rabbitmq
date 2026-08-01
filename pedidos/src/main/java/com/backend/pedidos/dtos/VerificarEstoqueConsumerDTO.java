package com.backend.pedidos.dtos;

import java.util.List;

public record VerificarEstoqueConsumerDTO(
        Long idPedido,
        Boolean possuiAlgumItemSemEstoque,
        List<PedidoItemConsumerDTO> listaPedidoItem
) {
}
