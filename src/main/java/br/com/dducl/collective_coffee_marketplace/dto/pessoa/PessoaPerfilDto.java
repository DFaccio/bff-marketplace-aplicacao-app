package br.com.dducl.collective_coffee_marketplace.dto.pessoa;

import br.com.dducl.collective_coffee_marketplace.dto.fornecedor.FornecedorCadastro;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.Setter;

@JsonSubTypes({@JsonSubTypes.Type(value = FornecedorCadastro.class, name = "FORNECEDOR"),
        @JsonSubTypes.Type(value = PessoaPerfilDto.class, name = "COMPRADOR"),
        @JsonSubTypes.Type(value = PessoaPerfilDto.class, name = "ADMINISTRADOR")})
@Getter
@Setter
public class PessoaPerfilDto {

}
