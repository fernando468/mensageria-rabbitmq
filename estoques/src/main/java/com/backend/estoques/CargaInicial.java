package com.backend.estoques;

import com.backend.estoques.models.Produto;
import com.backend.estoques.repositories.ProdutoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CargaInicial implements ApplicationRunner {
    private final ProdutoRepository produtoRepository;

    public CargaInicial(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Produto produtoUm = new Produto();
        produtoUm.setNome("Smartphone Samsung S25");
        produtoUm.setPreco(new BigDecimal("2999.99"));
        produtoUm.setQuantidadeEmEstoque(1L);

        Produto produtoDois = new Produto();
        produtoDois.setNome("TV LG 50");
        produtoDois.setPreco(new BigDecimal("1999.99"));
        produtoDois.setQuantidadeEmEstoque(2L);

        Produto produtoTres = new Produto();
        produtoTres.setNome("Geladeira Electrolux 410L");
        produtoTres.setPreco(new BigDecimal("1999.99"));
        produtoTres.setQuantidadeEmEstoque(3L);

        produtoRepository.saveAll(List.of(produtoUm, produtoDois, produtoTres));
    }
}
