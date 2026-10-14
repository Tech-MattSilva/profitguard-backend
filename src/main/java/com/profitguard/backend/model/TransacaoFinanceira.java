package com.profitguard.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacao_financeira")
@Getter
@Setter
public class TransacaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    private String categoria;

    private String descricao;

    private BigDecimal valor;

    private LocalDateTime dataTransacao;

    @PrePersist
    public void aoCriar() {
        if (this.dataTransacao == null) {
            this.dataTransacao = LocalDateTime.now();
        }
    }
}