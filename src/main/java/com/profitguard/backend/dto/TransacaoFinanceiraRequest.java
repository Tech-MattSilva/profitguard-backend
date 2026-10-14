package com.profitguard.backend.dto;

import com.profitguard.backend.model.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransacaoFinanceiraRequest {
    private TipoTransacao tipo;
    private String categoria;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime dataTransacao; // opcional
}