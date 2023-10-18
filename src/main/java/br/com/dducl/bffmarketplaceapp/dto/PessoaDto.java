package br.com.dducl.bffmarketplaceapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PessoaDto extends Dto {

    private String nome;

    private String identificador;

    private String email;

    private String telefone;

    private String dataCadastro;

    private boolean ativo;

    private List<String> chavesAtivas;
}
