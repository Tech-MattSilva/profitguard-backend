package com.profitguard.backend.dto;

import com.profitguard.backend.model.Produto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DashboardResponse {
    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal saldoCaixa;
    private int quantidadeProdutos;
    private List<Produto> produtosComEstoqueBaixo;
}