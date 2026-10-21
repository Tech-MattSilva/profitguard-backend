package com.profitguard.backend.dto;

import com.profitguard.backend.model.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovimentacaoEstoqueRequest {
    @NotNull(message = "O produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "O tipo é obrigatório (ENTRADA ou SAIDA)")
    private TipoMovimentacao tipo;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    private String motivo;
}