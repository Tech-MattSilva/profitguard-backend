package com.profitguard.backend.dto;

import com.profitguard.backend.model.ItemVenda;
import com.profitguard.backend.model.Venda;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VendaResponse {

    private Long id;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private List<ItemResponse> itens;

    @Getter
    @Setter
    public static class ItemResponse {
        private Long produtoId;
        private String produtoNome;
        private Integer quantidade;
        private BigDecimal precoUnitario;
        private BigDecimal subtotal;
    }

    public static VendaResponse fromEntity(Venda venda) {
        VendaResponse response = new VendaResponse();
        response.setId(venda.getId());
        response.setDataVenda(venda.getDataVenda());
        response.setValorTotal(venda.getValorTotal());
        response.setItens(venda.getItens().stream().map(VendaResponse::toItemResponse).toList());
        return response;
    }

    private static ItemResponse toItemResponse(ItemVenda item) {
        ItemResponse r = new ItemResponse();
        r.setProdutoId(item.getProduto().getId());
        r.setProdutoNome(item.getProduto().getNome());
        r.setQuantidade(item.getQuantidade());
        r.setPrecoUnitario(item.getPrecoUnitario());
        r.setSubtotal(item.getSubtotal());
        return r;
    }
}