package com.profitguard.backend.service;

import com.profitguard.backend.dto.RecomendacaoResponse;
import com.profitguard.backend.dto.RecomendacaoResponse.Prioridade;
import com.profitguard.backend.dto.RecomendacaoResponse.Tipo;
import com.profitguard.backend.model.Produto;
import com.profitguard.backend.repository.ItemVendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecomendacaoService {

    private static final int JANELA_DIAS = 30;             // período analisado
    private static final int DIAS_COBERTURA_ALVO = 30;     // quanto tempo queremos cobrir ao repor
    private static final int DIAS_COBERTURA_CRITICA = 7;   // abaixo disso é urgente

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FinanceiroService financeiroService;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    public List<RecomendacaoResponse> gerar() {
        LocalDateTime desde = LocalDateTime.now().minusDays(JANELA_DIAS);

        Map<Long, Long> vendidosPorProduto = new HashMap<>();
        for (Object[] linha : itemVendaRepository.somarVendasPorProduto(desde)) {
            vendidosPorProduto.put((Long) linha[0], ((Number) linha[1]).longValue());
        }

        BigDecimal saldo = financeiroService.calcularSaldo();
        List<RecomendacaoResponse> recomendacoes = new ArrayList<>();

        for (Produto produto : produtoService.listarTodos()) {
            long vendidos = vendidosPorProduto.getOrDefault(produto.getId(), 0L);
            double mediaDiaria = (double) vendidos / JANELA_DIAS;
            int estoque = produto.getQuantidadeDisponivel();

            boolean abaixoDoMinimo = estoque <= produto.getEstoqueMinimo();
            boolean coberturaCritica = mediaDiaria > 0 && (estoque / mediaDiaria) < DIAS_COBERTURA_CRITICA;

            if (abaixoDoMinimo || coberturaCritica) {
                recomendacoes.add(recomendarReposicao(produto, mediaDiaria, saldo, coberturaCritica));
            } else if (vendidos == 0 && estoque > 0) {
                recomendacoes.add(recomendarEstoqueParado(produto));
            }
        }

        recomendacoes.sort(Comparator.comparing(RecomendacaoResponse::getPrioridade));
        return recomendacoes;
    }

    private RecomendacaoResponse recomendarReposicao(Produto produto, double mediaDiaria,
                                                     BigDecimal saldo, boolean coberturaCritica) {
        int estoque = produto.getQuantidadeDisponivel();
        int alvo = Math.max((int) Math.ceil(mediaDiaria * DIAS_COBERTURA_ALVO), produto.getEstoqueMinimo() * 2);
        int quantidadeSugerida = Math.max(alvo - estoque, 1);
        BigDecimal custo = produto.getCustoUnitario().multiply(BigDecimal.valueOf(quantidadeSugerida));
        boolean caixaSuficiente = saldo.compareTo(custo) >= 0;

        String mensagem = "Repor " + quantidadeSugerida + " unidades (custo estimado R$ "
                + String.format("%.2f", custo) + "). "
                + (caixaSuficiente
                ? "O saldo em caixa cobre a compra."
                : "Atenção: o saldo em caixa (R$ " + String.format("%.2f", saldo) + ") não cobre essa compra.");

        RecomendacaoResponse r = new RecomendacaoResponse();
        r.setProdutoId(produto.getId());
        r.setProdutoNome(produto.getNome());
        r.setTipo(Tipo.REPOR_ESTOQUE);
        r.setPrioridade(estoque == 0 || coberturaCritica ? Prioridade.ALTA : Prioridade.MEDIA);
        r.setMensagem(mensagem);
        r.setQuantidadeSugerida(quantidadeSugerida);
        r.setCustoEstimado(custo);
        r.setCaixaSuficiente(caixaSuficiente);
        return r;
    }

    private RecomendacaoResponse recomendarEstoqueParado(Produto produto) {
        BigDecimal capitalParado = produto.getCustoUnitario()
                .multiply(BigDecimal.valueOf(produto.getQuantidadeDisponivel()));

        RecomendacaoResponse r = new RecomendacaoResponse();
        r.setProdutoId(produto.getId());
        r.setProdutoNome(produto.getNome());
        r.setTipo(Tipo.ESTOQUE_PARADO);
        r.setPrioridade(Prioridade.BAIXA);
        r.setMensagem("Sem vendas nos últimos " + JANELA_DIAS + " dias, com R$ "
                + String.format("%.2f", capitalParado)
                + " de capital parado em estoque. Evite novas compras e considere uma promoção.");
        return r;
    }
}
