package com.profitguard.backend.service;

import com.profitguard.backend.exception.EstoqueInsuficienteException;
import com.profitguard.backend.model.MovimentacaoEstoque;
import com.profitguard.backend.model.Produto;
import com.profitguard.backend.model.TipoMovimentacao;
import com.profitguard.backend.repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstoqueService {

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Transactional
    public MovimentacaoEstoque registrarMovimentacao(Long produtoId, TipoMovimentacao tipo, Integer quantidade, String motivo) {
        Produto produto = produtoService.buscarPorId(produtoId);

        aplicarNoEstoque(produto, tipo, quantidade);

        MovimentacaoEstoque movimentacao = new MovimentacaoEstoque();
        movimentacao.setProduto(produto);
        movimentacao.setTipo(tipo);
        movimentacao.setQuantidade(quantidade);
        movimentacao.setMotivo(motivo);

        return movimentacaoRepository.save(movimentacao);
    }

    public void aplicarNoEstoque(Produto produto, TipoMovimentacao tipo, Integer quantidade) {
        if (tipo == TipoMovimentacao.SAIDA && produto.getQuantidadeDisponivel() < quantidade) {
            throw new EstoqueInsuficienteException(
                    "Estoque insuficiente para o produto '" + produto.getNome() + "'. "
                            + "Disponível: " + produto.getQuantidadeDisponivel() + ", solicitado: " + quantidade);
        }

        int novaQuantidade = tipo == TipoMovimentacao.ENTRADA
                ? produto.getQuantidadeDisponivel() + quantidade
                : produto.getQuantidadeDisponivel() - quantidade;

        produto.setQuantidadeDisponivel(novaQuantidade);
        produtoService.criar(produto); // como o produto já tem id, o save() atualiza em vez de inserir
    }

    public List<MovimentacaoEstoque> historicoPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoIdOrderByDataMovimentacaoDesc(produtoId);
    }
}