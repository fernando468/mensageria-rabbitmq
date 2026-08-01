package com.backend.pedidos.models;

import com.backend.pedidos.enums.StatusPedidoEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private List<PedidoItem> listaPedidoItem = new ArrayList<>();

    @Column(name = "total")
    private BigDecimal total;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum statusPedido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PedidoItem> getListaPedidoItem() {
        return listaPedidoItem;
    }

    public void setListaPedidoItem(List<PedidoItem> listaPedidoItem) {
        this.listaPedidoItem = listaPedidoItem;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public StatusPedidoEnum getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedidoEnum statusPedido) {
        this.statusPedido = statusPedido;
    }
}
