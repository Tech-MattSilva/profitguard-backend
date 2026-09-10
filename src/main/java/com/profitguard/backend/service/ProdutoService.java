package com.profitguard.backend.service;

import com.profitguard.backend.model.Produto;
import com.profitguard.backend.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos(){
        return produtoRepository.findAll();
    }

    public Produto criar(Produto produto){
        return produtoRepository.save(produto);
    }
}
