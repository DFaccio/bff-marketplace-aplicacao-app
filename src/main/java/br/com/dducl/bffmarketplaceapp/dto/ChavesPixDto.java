package br.com.dducl.bffmarketplaceapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChavesPixDto extends Dto {

    @NotBlank(message = "Chave é um campo obrigatório")
    private String chave;

    @NotNull(message = "O estado da chave é obrigatório")
    private boolean ativo;
}
