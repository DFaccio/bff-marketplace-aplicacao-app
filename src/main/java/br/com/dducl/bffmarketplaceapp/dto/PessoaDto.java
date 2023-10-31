package br.com.dducl.bffmarketplaceapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id"})
public class PessoaDto extends Dto {

    @NotBlank(message = "Nome é um campo obrigatório")
    private String nome;

    @NotBlank(message = "Identificação (CPF/CNPJ) é um campo obrigatório")
    private String identificador;

    @Email(message = "Não é um e-mail válido")
    private String email;

    private String telefone;

    private String dataCadastro;

    private boolean ativo;

    private EnderecoDto endereco;

    private List<ChavesPixDto> chavesPix;
}