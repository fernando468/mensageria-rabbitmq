package com.backend.pedidos.configs;

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
    @Value("${rabbitmq.queue.consumer.estoque-estoque-verificado}")
    private String filaConsumerEstoqueVerificado;

    @Value("${rabbitmq.queue.consumer.estoque-produtos-lista}")
    private String filaConsumerEstoqueProdutosLista;

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
    public Queue estoqueVerificadoQueue() {
        return QueueBuilder.durable(filaConsumerEstoqueVerificado)
                .deadLetterExchange(exchangeDirectPedido)
                .deadLetterRoutingKey(filaConsumerEstoqueVerificado + "-dead-letter")
                .build();
    }

    @Bean
    public Queue estoqueProdutosListaDlqQueue() {
        return QueueBuilder
                .durable(filaConsumerEstoqueProdutosLista + "-dead-letter")
                .build();
    }

    @Bean
    public Queue estoqueVerificadoDlqQueue() {
        return QueueBuilder
                .durable(filaConsumerEstoqueVerificado + "-dead-letter")
                .build();
    }

    @Bean
    public Queue estoqueProdutosListaQueue() {
        return QueueBuilder.durable(filaConsumerEstoqueProdutosLista)
                .deadLetterExchange(exchangeDirectPedido)
                .deadLetterRoutingKey(filaConsumerEstoqueProdutosLista + "-dead-letter")
                .build();
    }

    @Bean
    public Binding estoqueVerificado() {
        return BindingBuilder
                .bind(estoqueVerificadoQueue())
                .to(exchange())
                .with(filaConsumerEstoqueVerificado);
    }

    @Bean
    public Binding estoqueVerificadoDlqBinding() {
        return BindingBuilder
                .bind(estoqueVerificadoDlqQueue())
                .to(exchange())
                .with(filaConsumerEstoqueVerificado + "-dead-letter");
    }

    @Bean
    public Binding estoqueProdutosLista() {
        return BindingBuilder
                .bind(estoqueProdutosListaQueue())
                .to(exchange())
                .with(filaConsumerEstoqueProdutosLista);
    }

    @Bean
    public Binding estoqueProdutosListaDlq() {
        return BindingBuilder
                .bind(estoqueProdutosListaDlqQueue())
                .to(exchange())
                .with(filaConsumerEstoqueProdutosLista + "-dead-letter");
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
