package br.com.dducl.bffmarketplaceapp.modelo.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "portfolio_produto")

public class PortfolioProdutos implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private Integer IdPortfolio;

    @Column
    private Integer IdProduto;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate dataCadastro;
}
