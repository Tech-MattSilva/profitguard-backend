package com.profitguard.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao_estoque")
@Getter
@Setter
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private Integer quantidade;

    private String motivo;

    private LocalDateTime dataMovimentacao;

    @PrePersist
    public void aoCriar() {
        this.dataMovimentacao = LocalDateTime.now();
    }
}