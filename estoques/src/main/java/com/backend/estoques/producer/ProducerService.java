package com.backend.estoques.producer;

import com.backend.estoques.dtos.ProdutoProducerDTO;
import com.backend.estoques.dtos.VerificarProdutoProducerDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class ProducerService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.queue.producer.estoque-produtos-lista}")
    private String filaProducerEstoqueProdutosLista;

    @Value("${rabbitmq.queue.producer.estoque-estoque-verificado}")
    private String filaProducerEstoqueVerificado;

    public ProducerService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void publicarListaProduto(List<ProdutoProducerDTO> listaProdutos) {
        amqpTemplate.convertAndSend(filaProducerEstoqueProdutosLista, objectMapper.writeValueAsString(listaProdutos));
    }

    public void publicarVerificarSePossuiEstoque(VerificarProdutoProducerDTO verificarProdutoProducerDTO) {
        amqpTemplate.convertAndSend(filaProducerEstoqueVerificado, objectMapper.writeValueAsString(verificarProdutoProducerDTO));
    }
}
