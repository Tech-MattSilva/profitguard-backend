package com.profitguard.backend.service;

import com.profitguard.backend.dto.ItemVendaRequest;
import com.profitguard.backend.dto.VendaRequest;
import com.profitguard.backend.exception.ResourceNotFoundException;
import com.profitguard.backend.model.ItemVenda;
import com.profitguard.backend.model.Produto;
import com.profitguard.backend.model.TipoMovimentacao;
import com.profitguard.backend.model.Venda;
import com.profitguard.backend.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private FinanceiroService financeiroService;

    @Transactional
    public Venda registrarVenda(VendaRequest request) {
        Venda venda = new Venda();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemVendaRequest itemRequest : request.getItens()) {
            Produto produto = produtoService.buscarPorId(itemRequest.getProdutoId());

            // dá baixa no estoque e registra no histórico (lança exceção se não houver saldo)
            estoqueService.registrarMovimentacao(
                    produto.getId(), TipoMovimentacao.SAIDA, itemRequest.getQuantidade(), "Venda");

            BigDecimal precoUnitario = produto.getPrecoVenda();
            BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(itemRequest.getQuantidade()));

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPrecoUnitario(precoUnitario);
            item.setSubtotal(subtotal);

            venda.adicionarItem(item);
            valorTotal = valorTotal.add(subtotal);
        }

        venda.setValorTotal(valorTotal);
        Venda vendaSalva = vendaRepository.save(venda);

        financeiroService.registrarReceitaDeVenda(valorTotal, vendaSalva.getId());

        return vendaSalva;
    }

    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada: id=" + id));
    }
}