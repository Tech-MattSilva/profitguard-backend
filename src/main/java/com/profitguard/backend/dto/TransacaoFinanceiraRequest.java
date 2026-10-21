package com.profitguard.backend.dto;

import com.profitguard.backend.model.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransacaoFinanceiraRequest {
    @NotNull(message = "O tipo é obrigatório (RECEITA ou DESPESA)")
    private TipoTransacao tipo;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    private String descricao;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal valor;

    private LocalDateTime dataTransacao;
}