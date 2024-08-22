package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaPerfilDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorCadastro extends PessoaPerfilDto {

    @NotBlank(message = "Razão Social é um campo obrigatório")
    private String razaoSocial;
}