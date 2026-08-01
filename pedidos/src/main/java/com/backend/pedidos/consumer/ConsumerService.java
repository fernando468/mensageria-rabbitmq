package com.backend.pedidos.consumer;

import com.backend.pedidos.dtos.ProdutoConsumerDTO;
import com.backend.pedidos.dtos.VerificarEstoqueConsumerDTO;
import com.backend.pedidos.services.PedidoService;
import com.backend.pedidos.services.ProdutoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class ConsumerService {
    private final ProdutoService produtoService;
    private final ObjectMapper objectMapper;
    private final PedidoService pedidoService;

    public ConsumerService(ProdutoService produtoService, ObjectMapper objectMapper, PedidoService pedidoService) {
        this.produtoService = produtoService;
        this.objectMapper = objectMapper;
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.consumer.estoque-produtos-lista}")
    public void estoqueProdutosLista(@Payload String payload) {
        List<ProdutoConsumerDTO> listaProdutoConsumer = objectMapper.readValue(payload, new TypeReference<List<ProdutoConsumerDTO>>() {});
        produtoService.salvarLista(listaProdutoConsumer);
    }

    @RabbitListener(queues = "${rabbitmq.queue.consumer.estoque-estoque-verificado}")
    public void estoqueVerificado(@Payload String payload) {
        VerificarEstoqueConsumerDTO verificarEstoqueConsumerDTO = objectMapper.readValue(payload, VerificarEstoqueConsumerDTO.class);
        pedidoService.atualizarPedido(verificarEstoqueConsumerDTO);
    }
}
