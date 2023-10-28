package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortfolioDto extends Dto{

    private Integer id;

    private Integer IdFornecedor;

    private String dataCriacao;

    private String dataVigencia;

    private boolean ativo;

    private List<Integer> produtosPortfolio;
    // no MER está como DetalhesPortfolio

}