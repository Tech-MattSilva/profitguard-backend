package com.profitguard.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendaRequest {
    @NotEmpty(message = "A venda precisa ter ao menos um item")
    @Valid
    private List<ItemVendaRequest> itens;
}