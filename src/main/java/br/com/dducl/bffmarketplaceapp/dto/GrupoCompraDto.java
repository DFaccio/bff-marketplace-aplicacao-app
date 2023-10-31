package br.com.dducl.bffmarketplaceapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoCompraDto extends Dto {

    private String dataCriacao;

    @NotBlank(message = "Nome do Grupo é obrigatório")
    private String nome;

    private boolean ativo;
}
