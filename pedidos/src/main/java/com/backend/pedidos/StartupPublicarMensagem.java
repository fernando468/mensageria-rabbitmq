package com.backend.pedidos;

import com.backend.pedidos.producer.ProducerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupPublicarMensagem implements ApplicationRunner {
    private final ProducerService producerService;

    public StartupPublicarMensagem(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        producerService.buscarEstoque();
    }
}
