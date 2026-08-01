package com.backend.estoques.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${rabbitmq.queue.producer.verificar-estoque}")
    private String filaProducerVerificarEstoque;

    @Value("${rabbitmq.queue.consumer.estoque-estoque-verificado}")
    private String filaProducerEstoqueVerificado;

    @Value("${rabbitmq.queue.producer.estoque-produtos}")
    private String filaProducerEstoqueProdutos;

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
    public Queue pedidoQueue() {
        return new Queue(filaProducerVerificarEstoque, true);
    }

    @Bean
    public Queue estoqueVerificadoQueue() {
        return new Queue(filaProducerEstoqueVerificado, true );
    }

    @Bean
    public Queue estoqueProdutosQueue() {
        return new Queue(filaProducerEstoqueProdutos, true );
    }

    @Bean
    public Queue estoqueProdutosListaQueue() {
        return new Queue(filaConsumerEstoqueProdutosLista, true );
    }

    @Bean
    public Binding verificarEstoqueBinding() {
        return BindingBuilder
                .bind(pedidoQueue())
                .to(exchange())
                .with(filaProducerVerificarEstoque);
    }

    @Bean
    public Binding estoqueVerificado() {
        return BindingBuilder
                .bind(estoqueVerificadoQueue())
                .to(exchange())
                .with(filaProducerEstoqueVerificado);
    }

    @Bean
    public Binding estoqueProdutos() {
        return BindingBuilder
                .bind(estoqueProdutosQueue())
                .to(exchange())
                .with(filaProducerEstoqueProdutos);
    }

    @Bean
    public Binding estoqueProdutosLista() {
        return BindingBuilder
                .bind(estoqueProdutosListaQueue())
                .to(exchange())
                .with(filaProducerEstoqueProdutos);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
