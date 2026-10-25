package com.profitguard.backend.service;

import com.profitguard.backend.dto.CotacaoDolar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CambioService {

    private static final Logger log = LoggerFactory.getLogger(CambioService.class);

    // Série 10813 do Banco Central (SGS): dólar comercial, últimos 20 dias úteis (~1 mês)
    private static final String URL =
            "https://api.bcb.gov.br/dados/serie/bcdata.sgs.10813/dados/ultimos/20?formato=json";

    private final RestClient restClient;

    private Optional<CotacaoDolar> cache = Optional.empty();
    private Instant validoAte = Instant.EPOCH;

    public CambioService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(15000);
        this.restClient = RestClient.builder().requestFactory(factory).build();
    }

    public synchronized Optional<CotacaoDolar> obterCotacao() {
        if (Instant.now().isBefore(validoAte)) {
            return cache;
        }
        try {
            cache = Optional.of(buscar());
            validoAte = Instant.now().plus(Duration.ofHours(1));
            log.info("Cotação do dólar atualizada: {}", cache.get());
        } catch (Exception e) {
            log.warn("Não foi possível obter a cotação do dólar: {}", e.getMessage());
            validoAte = Instant.now().plus(Duration.ofMinutes(5));
        }
        return cache;
    }

    private CotacaoDolar buscar() {
        List<Map<String, String>> serie = restClient.get()
                .uri(URL)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Map<String, String>>>() {});

        if (serie == null || serie.size() < 2) {
            throw new IllegalStateException("Série de câmbio vazia");
        }

        Map<String, String> primeiro = serie.get(0);
        Map<String, String> ultimo = serie.get(serie.size() - 1);

        BigDecimal inicial = new BigDecimal(primeiro.get("valor"));
        BigDecimal atual = new BigDecimal(ultimo.get("valor"));
        BigDecimal variacao = atual.subtract(inicial)
                .multiply(BigDecimal.valueOf(100))
                .divide(inicial, 2, RoundingMode.HALF_UP);

        return new CotacaoDolar(atual, ultimo.get("data"), variacao);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void aquecerCache() {
        new Thread(this::obterCotacao, "cambio-warmup").start();
    }
}