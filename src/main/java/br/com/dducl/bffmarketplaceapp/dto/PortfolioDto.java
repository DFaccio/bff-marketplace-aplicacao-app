package br.com.dducl.bffmarketplaceapp.dto;

import br.com.dducl.bffmarketplaceapp.util.enums.StatusPortifolio;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PortfolioDto extends Dto{

    private Integer id;

    private String descricao;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataVigencia;

    private StatusPortifolio status;

    private FornecedorDto fornecedor;

    private List<Integer> produtos; // no MER está como DetalhesPortfolio

}