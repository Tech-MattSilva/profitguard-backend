package com.profitguard.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RecomendacaoResponse {

    public enum Tipo { REPOR_ESTOQUE, ESTOQUE_PARADO }

    public enum Prioridade { ALTA, MEDIA, BAIXA }

    private Long produtoId;
    private String produtoNome;
    private Tipo tipo;
    private Prioridade prioridade;
    private String mensagem;
    private Integer quantidadeSugerida;
    private BigDecimal custoEstimado;
    private Boolean caixaSuficiente;
}
