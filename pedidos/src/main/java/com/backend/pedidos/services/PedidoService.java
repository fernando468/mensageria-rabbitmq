package com.backend.pedidos.services;

import com.backend.pedidos.dtos.*;
import com.backend.pedidos.enums.StatusPedidoEnum;
import com.backend.pedidos.models.Pedido;
import com.backend.pedidos.models.PedidoItem;
import com.backend.pedidos.models.Produto;
import com.backend.pedidos.producer.ProducerService;
import com.backend.pedidos.repositories.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final ProducerService producerService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService, ProducerService producerService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
        this.producerService = producerService;
    }

    public void criar(CriarPedidoRequestDTO criarPedidoRequestDTO) {
        List<Produto> listaProduto = produtoService.buscarTodos();

        Pedido pedido = new Pedido();
        BigDecimal totalPedido = BigDecimal.ZERO;

        for (PeditoItemRequestDTO item : criarPedidoRequestDTO.listaPedidoItem()) {
            Produto produto = listaProduto
                    .stream()
                    .filter(produtoParam -> produtoParam.getId().equals(item.idProduto()))
                    .findFirst()
                    .orElse(null);
            if (produto != null) {
                PedidoItem pedidoItem = new PedidoItem();
                BigDecimal total = produto.getPreco().multiply(BigDecimal.valueOf(item.quantidade()));
                totalPedido = totalPedido.add(total);

                pedidoItem.setProduto(produto);
                pedidoItem.setPreco(produto.getPreco());
                pedidoItem.setQuantidade(item.quantidade());
                pedidoItem.setTotal(total);
                pedidoItem.setPedido(pedido);
                pedido.setStatusPedido(StatusPedidoEnum.PENDENTE);
                pedido.getListaPedidoItem().add(pedidoItem);
            }
        }

        pedido.setTotal(totalPedido);

        List<PedidoItemProducerDTO> listaPedidoItemProducerDTO = new ArrayList<>();

        pedido.getListaPedidoItem().forEach(pedidoItem -> {
            listaPedidoItemProducerDTO.add(new PedidoItemProducerDTO(pedidoItem.getProduto().getId(), pedidoItem.getQuantidade()));
        });


        pedidoRepository.save(pedido);

        VerificarEstoqueProducerDTO verificarEstoqueProducerDTO = new VerificarEstoqueProducerDTO(pedido.getId(), listaPedidoItemProducerDTO);
        producerService.verificarEstoque(verificarEstoqueProducerDTO);
    }

    public void atualizarPedido(VerificarEstoqueConsumerDTO verificarEstoqueConsumerDTO) {
        Pedido pedido = pedidoRepository.findById(verificarEstoqueConsumerDTO.idPedido()).orElse(null);
        if (pedido == null) return;

        if (verificarEstoqueConsumerDTO.possuiAlgumItemSemEstoque()) {
            pedido.setStatusPedido(StatusPedidoEnum.CANCELADO);
        } else {
            pedido.setStatusPedido(StatusPedidoEnum.APROVADO);
        }

        pedidoRepository.save(pedido);

    }

    public List<PedidoResponseDTO> buscarTodos() {
        List<Pedido> listaPedido = pedidoRepository.findAll();
        List<PedidoResponseDTO> listaPedidoResponseDTO = listaPedido.stream()
                .map(pedido -> {
                    List<PedidoItemResponseDTO> listaPedidoItemResponse = pedido.getListaPedidoItem()
                            .stream()
                            .map(pedidoItem -> new PedidoItemResponseDTO(pedidoItem.getQuantidade(), pedidoItem.getPreco(), pedidoItem.getTotal()))
                            .toList();

                    return new PedidoResponseDTO(pedido.getId(), pedido.getTotal(), pedido.getStatusPedido(), listaPedidoItemResponse);
                })
                .toList();
        return listaPedidoResponseDTO;
    }
}
