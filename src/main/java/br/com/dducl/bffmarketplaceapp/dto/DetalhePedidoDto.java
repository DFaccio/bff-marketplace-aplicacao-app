package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalhePedidoDto {

    private PedidoDto pedido;

    private ProdutoDto produto;

    private FornecedorDto fornecedor;

    private int quantidade;

    private double  valorTotal;

}
