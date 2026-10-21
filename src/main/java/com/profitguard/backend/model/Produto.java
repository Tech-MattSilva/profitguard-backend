package com.profitguard.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A quantidade é obrigatória")
    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    private Integer quantidadeDisponivel;

    @NotNull(message = "O estoque mínimo é obrigatório")
    @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
    private Integer estoqueMinimo;

    @NotNull(message = "O custo é obrigatório")
    @PositiveOrZero(message = "O custo não pode ser negativo")
    private BigDecimal custoUnitario;

    @NotNull(message = "O preço de venda é obrigatório")
    @PositiveOrZero(message = "O preço não pode ser negativo")
    private BigDecimal precoVenda;
}
