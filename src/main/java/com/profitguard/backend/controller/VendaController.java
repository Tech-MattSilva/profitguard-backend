package com.profitguard.backend.controller;

import com.profitguard.backend.dto.VendaRequest;
import com.profitguard.backend.dto.VendaResponse;
import com.profitguard.backend.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public VendaResponse registrar(@Valid @RequestBody VendaRequest request) {
        return VendaResponse.fromEntity(vendaService.registrarVenda(request));
    }

    @GetMapping
    public List<VendaResponse> listar() {
        return vendaService.listarTodas().stream().map(VendaResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public VendaResponse buscarPorId(@PathVariable Long id) {
        return VendaResponse.fromEntity(vendaService.buscarPorId(id));
    }
}