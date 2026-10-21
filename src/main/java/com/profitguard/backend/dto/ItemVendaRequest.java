package com.profitguard.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaRequest {
    private Long produtoId;
    private Integer quantidade;
}