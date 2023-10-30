package br.com.dducl.bffmarketplaceapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDto extends Dto {

    private String apelido;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String complemento;

    private String cep;
}
