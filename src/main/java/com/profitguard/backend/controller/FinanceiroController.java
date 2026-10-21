package com.profitguard.backend.controller;

import com.profitguard.backend.dto.TransacaoFinanceiraRequest;
import com.profitguard.backend.model.TransacaoFinanceira;
import com.profitguard.backend.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    @GetMapping("/transacoes")
    public List<TransacaoFinanceira> listar() {
        return financeiroService.listarTodas();
    }

    @PostMapping("/transacoes")
    public TransacaoFinanceira registrar(@Valid @RequestBody TransacaoFinanceiraRequest request) {
        return financeiroService.registrar(request);
    }

    @DeleteMapping("/transacoes/{id}")
    public void deletar(@PathVariable Long id) {
        financeiroService.deletar(id);
    }

    @GetMapping("/saldo")
    public BigDecimal saldo() {
        return financeiroService.calcularSaldo();
    }
}