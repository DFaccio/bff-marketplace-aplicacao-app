package br.com.dducl.bffmarketplaceapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorDto extends Dto {

    private PessoaDto informacoes;

    @NotBlank(message = "Razão Social é um campo obrigatório")
    private String razaoSocial;
}
