package com.backend.pedidos.controllers;

import com.backend.pedidos.dtos.CriarPedidoRequestDTO;
import com.backend.pedidos.dtos.PedidoResponseDTO;
import com.backend.pedidos.services.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/criar")
    public ResponseEntity<Void> criar(@RequestBody CriarPedidoRequestDTO criarPedidoRequestDTO) {
        pedidoService.criar(criarPedidoRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<PedidoResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }
}
