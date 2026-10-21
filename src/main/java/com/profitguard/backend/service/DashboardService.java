package com.profitguard.backend.service;

import com.profitguard.backend.dto.DashboardResponse;
import com.profitguard.backend.model.TipoTransacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FinanceiroService financeiroService;

    public DashboardResponse gerar() {
        BigDecimal receitas = financeiroService.somarPorTipo(TipoTransacao.RECEITA);
        BigDecimal despesas = financeiroService.somarPorTipo(TipoTransacao.DESPESA);

        DashboardResponse response = new DashboardResponse();
        response.setTotalReceitas(receitas);
        response.setTotalDespesas(despesas);
        response.setSaldoCaixa(receitas.subtract(despesas));
        response.setQuantidadeProdutos(produtoService.listarTodos().size());
        response.setProdutosComEstoqueBaixo(produtoService.listarComEstoqueBaixo());
        return response;
    }
}
