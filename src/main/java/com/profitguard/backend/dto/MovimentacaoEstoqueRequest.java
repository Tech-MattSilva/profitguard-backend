package com.profitguard.backend.dto;

import com.profitguard.backend.model.TipoMovimentacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoEstoqueRequest {
    private Long produtoId;
    private TipoMovimentacao tipo;
    private Integer quantidade;
    private String motivo;
}