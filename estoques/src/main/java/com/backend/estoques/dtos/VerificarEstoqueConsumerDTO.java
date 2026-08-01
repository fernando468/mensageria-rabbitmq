package com.backend.estoques.dtos;

import java.util.List;

public record VerificarEstoqueConsumerDTO(
        Long idPedido,
        List<PedidoItemConsumerDTO> listaProdutoItem
) {
}
