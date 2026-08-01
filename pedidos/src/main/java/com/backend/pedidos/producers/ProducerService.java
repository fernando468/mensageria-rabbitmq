package com.backend.pedidos.producers;

import com.backend.pedidos.dtos.BuscarEstoqueProducerDTO;
import com.backend.pedidos.dtos.VerificarEstoqueProducerDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProducerService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.queue.producer.verificar-estoque}")
    private String filaProducerVerificarEstoque;


    @Value("${rabbitmq.queue.producer.estoque-produtos}")
    private String filaProducerEstoqueProdutos;

    public ProducerService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void verificarEstoque(VerificarEstoqueProducerDTO verificarEstoqueProducer) {
        amqpTemplate.convertAndSend(filaProducerVerificarEstoque, objectMapper.writeValueAsString(verificarEstoqueProducer));
    }

    public void buscarEstoque() {
        amqpTemplate.convertAndSend(filaProducerEstoqueProdutos, objectMapper.writeValueAsString(new BuscarEstoqueProducerDTO(UUID.randomUUID(), LocalDateTime.now())));
    }
}
