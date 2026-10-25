package com.profitguard.backend.dto;

import java.math.BigDecimal;

public record CotacaoDolar(BigDecimal valorAtual, String dataReferencia, BigDecimal variacaoPercentual) {
}