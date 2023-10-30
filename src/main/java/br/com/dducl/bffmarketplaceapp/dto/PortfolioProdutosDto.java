package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PortfolioProdutosDto extends Dto{
    private Integer id;

    private PortfolioDto portfolio;

    private ProdutoDto produto;

    private LocalDateTime dataCriacao;
}
