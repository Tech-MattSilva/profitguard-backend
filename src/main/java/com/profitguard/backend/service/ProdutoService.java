package com.profitguard.backend.service;

import com.profitguard.backend.exception.ResourceNotFoundException;
import com.profitguard.backend.model.Produto;
import com.profitguard.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: id=" + id));
    }

    public Produto criar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizar(Long id, Produto dadosAtualizados) {
        Produto produto = buscarPorId(id);
        produto.setNome(dadosAtualizados.getNome());
        produto.setQuantidadeDisponivel(dadosAtualizados.getQuantidadeDisponivel());
        produto.setEstoqueMinimo(dadosAtualizados.getEstoqueMinimo());
        produto.setCustoUnitario(dadosAtualizados.getCustoUnitario());
        produto.setPrecoVenda(dadosAtualizados.getPrecoVenda());
        produto.setInsumoImportado(dadosAtualizados.getInsumoImportado());
        return produtoRepository.save(produto);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorId(id);
        produtoRepository.delete(produto);
    }

    public List<Produto> listarComEstoqueBaixo() {
        return produtoRepository.findComEstoqueBaixo();
    }

}