package br.com.dducl.bffmarketplaceapp.dto;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDto extends Dto{

    private String conteudo;

    private String descricao;

    private boolean disponivel;

    private int quantidade;

    private BigDecimal valor;

    private String dataCriacao;

    private FornecedorDto fornecedor;

}
