package br.com.dducl.bffmarketplaceapp.dto;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.GrupoCompra;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto  extends Dto {

    private boolean ativo;

    private UsuarioDto usuario;

    private EnderecoDto enderecoEntrega;

//    TODO
//    private GrupoCompraDto grupoCompra;

    private PessoaDto pessoa;

    private String codigoRastreamento;

    private double valortotal;

}
