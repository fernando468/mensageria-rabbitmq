package com.backend.estoques.services;

import com.backend.estoques.dtos.*;
import com.backend.estoques.models.Produto;
import com.backend.estoques.producers.ProducerService;
import com.backend.estoques.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final ProducerService producerService;

    public ProdutoService(ProdutoRepository produtoRepository, ProducerService producerService) {
        this.produtoRepository = produtoRepository;
        this.producerService = producerService;
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public void enviarProdutos() {
        List<Produto> listaProduto = buscarTodos();
        List<ProdutoProducerDTO> listaProdutoProducer = new ArrayList<>();
        for (Produto produto : listaProduto) {
            listaProdutoProducer.add(new ProdutoProducerDTO(produto.getId(), produto.getNome(), produto.getPreco()));
        }
        producerService.publicarListaProduto(listaProdutoProducer);
    }

    public void verificarSePossuiEstoque(VerificarEstoqueConsumerDTO verificarEstoqueConsumer) {
        List<Produto> listaProdutos = buscarTodos();
        List<Produto> listaProdutoAtualizadoParaSalvar = new ArrayList<>();
        List<PedidoItemProducerDTO> listaPedidoItem = new ArrayList<>();
        boolean possuiAlgumItemSemEstoque = false;

        for (PedidoItemConsumerDTO pedidoItemConsumerDTO : verificarEstoqueConsumer.listaProdutoItem()) {
            Produto produto = listaProdutos
                    .stream()
                    .filter(produtoParam -> produtoParam.getId().equals(pedidoItemConsumerDTO.idProduto()))
                    .findFirst()
                    .orElse(null);
            if (produto == null) {
                possuiAlgumItemSemEstoque = true;
                break;
            };
            if (produto.getQuantidadeEmEstoque() <= 0 || pedidoItemConsumerDTO.quantidade() > produto.getQuantidadeEmEstoque()) {
                possuiAlgumItemSemEstoque = true;
                listaPedidoItem.add(new PedidoItemProducerDTO(pedidoItemConsumerDTO.idProduto(), produto.getQuantidadeEmEstoque()));
            }

            produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() - pedidoItemConsumerDTO.quantidade());

            listaProdutoAtualizadoParaSalvar.add(produto);
        }

        produtoRepository.saveAll(listaProdutoAtualizadoParaSalvar);

        VerificarProdutoProducerDTO verificarProdutoProducerDTO = new VerificarProdutoProducerDTO(
                verificarEstoqueConsumer.idPedido(),
                possuiAlgumItemSemEstoque,
                listaPedidoItem);

        producerService.publicarVerificarSePossuiEstoque(verificarProdutoProducerDTO);
    }

}
