package com.backend.pedidos.services;

import com.backend.pedidos.dtos.ProdutoConsumerDTO;
import com.backend.pedidos.models.Produto;
import com.backend.pedidos.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public void salvarLista(List<ProdutoConsumerDTO> listaProdutoConsumer) {
        List<Produto> listaProdutoParaSalvar = new ArrayList<>();
        for (ProdutoConsumerDTO produtoConsumerDTO : listaProdutoConsumer) {
            Produto produto = new Produto();
            produto.setId(produtoConsumerDTO.id());
            produto.setNome(produtoConsumerDTO.nome());
            produto.setPreco(produtoConsumerDTO.preco());
            listaProdutoParaSalvar.add(produto);
        }

        produtoRepository.saveAll(listaProdutoParaSalvar);
    }
}
