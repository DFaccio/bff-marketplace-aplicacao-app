package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDto extends Dto{

    private Integer id;

    private String conteudo;

    private String descricao;

    private boolean disponivel;

    private int quantidade;

    private BigDecimal valor;

    private String dataCriacao;

}
