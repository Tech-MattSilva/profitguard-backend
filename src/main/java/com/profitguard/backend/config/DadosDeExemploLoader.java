package com.profitguard.backend.config;

import com.profitguard.backend.dto.ItemVendaRequest;
import com.profitguard.backend.dto.TransacaoFinanceiraRequest;
import com.profitguard.backend.dto.VendaRequest;
import com.profitguard.backend.model.Produto;
import com.profitguard.backend.model.TipoTransacao;
import com.profitguard.backend.service.FinanceiroService;
import com.profitguard.backend.service.ProdutoService;
import com.profitguard.backend.service.VendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("!prod")
public class DadosDeExemploLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DadosDeExemploLoader.class);

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private FinanceiroService financeiroService;

    @Override
    public void run(String... args) {
        // evita duplicar dados se o banco já tiver conteúdo
        if (!produtoService.listarTodos().isEmpty()) {
            return;
        }

        Produto cimento = criarProduto("Cimento 50kg", 100, 20, "28.50", "39.90");
        Produto areia = criarProduto("Areia media", 40, 10, "85.00", "130.00");
        Produto tijolo = criarProduto("Tijolo 8 furos", 2000, 500, "0.80", "1.20");
        criarProduto("Prego 18x27 kg", 8, 10, "12.00", "18.00");
        criarProduto("Tinta acrilica 18L", 25, 5, "180.00", "290.00");

        registrarVenda(item(cimento, 30), item(areia, 5));
        registrarVenda(item(cimento, 25), item(tijolo, 300));
        registrarVenda(item(cimento, 35), item(areia, 7), item(tijolo, 300));

        despesa("Aluguel", "Aluguel da loja", "2500.00");
        despesa("Energia", "Conta de energia", "420.00");
        despesa("Fornecedor", "Pagamento de fornecedor", "1500.00");

        log.info("Dados de exemplo carregados (produtos, vendas e despesas).");
    }

    private Produto criarProduto(String nome, int quantidade, int minimo, String custo, String preco) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setQuantidadeDisponivel(quantidade);
        produto.setEstoqueMinimo(minimo);
        produto.setCustoUnitario(new BigDecimal(custo));
        produto.setPrecoVenda(new BigDecimal(preco));
        return produtoService.criar(produto);
    }

    private ItemVendaRequest item(Produto produto, int quantidade) {
        ItemVendaRequest item = new ItemVendaRequest();
        item.setProdutoId(produto.getId());
        item.setQuantidade(quantidade);
        return item;
    }

    private void registrarVenda(ItemVendaRequest... itens) {
        VendaRequest request = new VendaRequest();
        request.setItens(List.of(itens));
        vendaService.registrarVenda(request);
    }

    private void despesa(String categoria, String descricao, String valor) {
        TransacaoFinanceiraRequest request = new TransacaoFinanceiraRequest();
        request.setTipo(TipoTransacao.DESPESA);
        request.setCategoria(categoria);
        request.setDescricao(descricao);
        request.setValor(new BigDecimal(valor));
        financeiroService.registrar(request);
    }
}
