package com.profitguard.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int quantidadeDisponivel;
    private int estoqueMinimo;
    private BigDecimal custoUnitario;
    private BigDecimal precoVenda;
}
