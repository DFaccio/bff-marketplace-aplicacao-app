package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GrupoCompraFullDto extends GrupoCompraDto {

    private List<PessoaDto> pessoas;
}
