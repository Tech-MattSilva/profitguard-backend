package com.profitguard.backend.controller;

import com.profitguard.backend.dto.MovimentacaoEstoqueRequest;
import com.profitguard.backend.model.MovimentacaoEstoque;
import com.profitguard.backend.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @PostMapping("/movimentacoes")
    public MovimentacaoEstoque registrar(@Valid @RequestBody MovimentacaoEstoqueRequest request) {
        return estoqueService.registrarMovimentacao(
                request.getProdutoId(), request.getTipo(), request.getQuantidade(), request.getMotivo());
    }

    @GetMapping("/movimentacoes/produto/{produtoId}")
    public List<MovimentacaoEstoque> historico(@PathVariable Long produtoId) {
        return estoqueService.historicoPorProduto(produtoId);
    }
}