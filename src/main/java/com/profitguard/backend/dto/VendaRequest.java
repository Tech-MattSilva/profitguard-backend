package com.profitguard.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendaRequest {
    private List<ItemVendaRequest> itens;
}