package com.profitguard.backend.controller;

import com.profitguard.backend.dto.CotacaoDolar;
import com.profitguard.backend.service.CambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cenario")
public class CenarioController {

    @Autowired
    private CambioService cambioService;

    @GetMapping("/dolar")
    public ResponseEntity<Object> dolar() {
        Optional<CotacaoDolar> cotacao = cambioService.obterCotacao();
        if (cotacao.isPresent()) {
            return ResponseEntity.ok(cotacao.get());
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("erro", "Cotação do dólar indisponível no momento"));
    }
}