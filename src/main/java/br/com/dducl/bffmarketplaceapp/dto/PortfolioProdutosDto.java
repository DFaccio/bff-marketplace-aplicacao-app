package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PortfolioProdutosDto extends Dto{
    private Integer id;

    private List<ProdutoDto> produto;

    private LocalDateTime dataCriacao;
}
