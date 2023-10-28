package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PortifolioDto extends Dto{

    private Integer id;

    private Integer IdFornecedor;

    private String dataCriacao;

    private String dataVigencia;

    private boolean ativo;

    private List<Integer> produtosPortifolio;
    // no MER está como DetalhesPortifolio

}