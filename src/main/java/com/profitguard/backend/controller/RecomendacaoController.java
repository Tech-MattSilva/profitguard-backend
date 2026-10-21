package com.profitguard.backend.controller;

import com.profitguard.backend.dto.RecomendacaoResponse;
import com.profitguard.backend.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping
    public List<RecomendacaoResponse> listar() {
        return recomendacaoService.gerar();
    }
}
