package com.profitguard.backend.repository;

import com.profitguard.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p WHERE p.quantidadeDisponivel <= p.estoqueMinimo")
    List<Produto> findComEstoqueBaixo();

}

