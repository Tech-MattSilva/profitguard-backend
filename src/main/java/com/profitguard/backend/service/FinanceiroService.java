package com.profitguard.backend.service;

import com.profitguard.backend.dto.TransacaoFinanceiraRequest;
import com.profitguard.backend.exception.ResourceNotFoundException;
import com.profitguard.backend.model.TipoTransacao;
import com.profitguard.backend.model.TransacaoFinanceira;
import com.profitguard.backend.repository.TransacaoFinanceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinanceiroService {

    @Autowired
    private TransacaoFinanceiraRepository transacaoRepository;

    public List<TransacaoFinanceira> listarTodas() {
        return transacaoRepository.findAll();
    }

    public TransacaoFinanceira registrar(TransacaoFinanceiraRequest request) {
        TransacaoFinanceira transacao = new TransacaoFinanceira();
        transacao.setTipo(request.getTipo());
        transacao.setCategoria(request.getCategoria());
        transacao.setDescricao(request.getDescricao());
        transacao.setValor(request.getValor());
        transacao.setDataTransacao(request.getDataTransacao());
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id) {
        TransacaoFinanceira transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada: id=" + id));
        transacaoRepository.delete(transacao);
    }

    public BigDecimal somarPorTipo(TipoTransacao tipo) {
        return transacaoRepository.findByTipo(tipo).stream()
                .map(TransacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calcularSaldo() {
        return somarPorTipo(TipoTransacao.RECEITA).subtract(somarPorTipo(TipoTransacao.DESPESA));
    }
}
