package com.backend.estoques.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.consumer.verificar-estoque}")
    private String filaConsumerVerificarEstoque;

    @Value("${rabbitmq.queue.consumer.estoque-produtos}")
    private String filaConsumerEstoqueProdutos;

    @Value("${rabbitmq.queue.exchange-pedidos}")
    private String exchangeDirectPedido;

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchangeDirectPedido);
    }

    @Bean
    public Queue pedidoQueue() {
        return QueueBuilder
                .durable(filaConsumerVerificarEstoque)
                .deadLetterExchange(exchangeDirectPedido)
                .deadLetterRoutingKey(filaConsumerVerificarEstoque + "-dead-letter")
                .build();
    }

    @Bean
    public Queue pedidoDlqQueue() {
        return QueueBuilder
                .durable(filaConsumerVerificarEstoque + "-dead-letter")
                .build();
    }

    @Bean
    public Queue estoqueProdutosQueue() {
        return QueueBuilder
                .durable(filaConsumerEstoqueProdutos)
                .deadLetterExchange(exchangeDirectPedido)
                .deadLetterRoutingKey(filaConsumerEstoqueProdutos + "-dead-letter")
                .build();
    }

    @Bean
    public Queue estoqueProdutosDlqQueue() {
        return QueueBuilder
                .durable(filaConsumerEstoqueProdutos + "-dead-letter")
                .build();
    }

    @Bean
    public Binding verificarEstoqueBinding() {
        return BindingBuilder
                .bind(pedidoQueue())
                .to(exchange())
                .with(filaConsumerVerificarEstoque);
    }

    @Bean
    public Binding verificarEstoqueDlqBinding() {
        return BindingBuilder
                .bind(pedidoDlqQueue())
                .to(exchange())
                .with(filaConsumerVerificarEstoque + "-dead-letter");
    }

    @Bean
    public Binding estoqueProdutosDlqBinding() {
        return BindingBuilder
                .bind(estoqueProdutosDlqQueue())
                .to(exchange())
                .with(filaConsumerVerificarEstoque + "-dead-letter");
    }

    @Bean
    public Binding estoqueProdutosBinding() {
        return BindingBuilder
                .bind(estoqueProdutosQueue())
                .to(exchange())
                .with(filaConsumerEstoqueProdutos);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}