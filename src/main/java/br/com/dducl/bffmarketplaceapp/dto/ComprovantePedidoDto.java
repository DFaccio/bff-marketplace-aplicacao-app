package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComprovantePedidoDto {

    private boolean ativo;

    private PedidoDto pedido;

    private String comprovante;

    private String dataCriacao;
}
