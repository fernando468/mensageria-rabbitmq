package com.backend.estoques.consumers;

import com.backend.estoques.dtos.VerificarEstoqueConsumerDTO;
import com.backend.estoques.services.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class ConsumerService {
    private final ProdutoService produtoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConsumerService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.consumer.estoque-produtos}")
    public void receberBuscarProdutos(@Payload String payload) {
        produtoService.enviarProdutos();
    }

    @RabbitListener(queues = "${rabbitmq.queue.consumer.verificar-estoque}")
    public void verificarEstoque(@Payload String payload) {
        VerificarEstoqueConsumerDTO verificarEstoqueConsumer = objectMapper.readValue(payload, VerificarEstoqueConsumerDTO.class);
        produtoService.verificarSePossuiEstoque(verificarEstoqueConsumer);
    }

}
