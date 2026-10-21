package com.profitguard.backend.repository;

import com.profitguard.backend.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {

    @Query("SELECT i.produto.id, SUM(i.quantidade) FROM ItemVenda i " +
            "WHERE i.venda.dataVenda >= :desde GROUP BY i.produto.id")
    List<Object[]> somarVendasPorProduto(@Param("desde") LocalDateTime desde);
}
