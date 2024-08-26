package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorDto extends PessoaDto {

    @NotBlank(message = "Razão Social é um campo obrigatório")
    @Schema(example = "Café 5 estrelas")
    private String razaoSocial;
}
