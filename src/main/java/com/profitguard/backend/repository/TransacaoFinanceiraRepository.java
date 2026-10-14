package com.profitguard.backend.repository;

import com.profitguard.backend.model.TipoTransacao;
import com.profitguard.backend.model.TransacaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoFinanceiraRepository extends JpaRepository<TransacaoFinanceira, Long> {

    List<TransacaoFinanceira> findByTipo(TipoTransacao tipo);
}