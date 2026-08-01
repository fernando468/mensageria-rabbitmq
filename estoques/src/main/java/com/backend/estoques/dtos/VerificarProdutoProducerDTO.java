package com.backend.estoques.dtos;

import java.util.List;

public record VerificarProdutoProducerDTO(
        Long idPedido,
        Boolean possuiAlgumItemSemEstoque,
        List<PedidoItemProducerDTO> listaPedidoItem
) {
}
