package br.com.dducl.collective_coffee_marketplace.dto;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GrupoCompraFullDto extends GrupoCompraDto {

    private List<PessoaDto> pessoas;
}
