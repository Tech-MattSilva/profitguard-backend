package com.profitguard.backend.repository;

import com.profitguard.backend.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
