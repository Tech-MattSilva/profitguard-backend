package com.profitguard.backend.repository;

import com.profitguard.backend.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    List<MovimentacaoEstoque> findByProdutoIdOrderByDataMovimentacaoDesc(Long produtoId);
}
