package com.backend.pedidos.dtos;

import java.util.List;

public record VerificarEstoqueProducerDTO(
        Long idPedido,
        List<PedidoItemProducerDTO> listaProdutoItem
) {
}
