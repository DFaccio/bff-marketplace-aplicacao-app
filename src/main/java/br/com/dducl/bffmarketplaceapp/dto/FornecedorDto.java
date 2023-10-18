package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorDto extends Dto {

    private PessoaDto informacoes;

    private String razaoSocial;
}
