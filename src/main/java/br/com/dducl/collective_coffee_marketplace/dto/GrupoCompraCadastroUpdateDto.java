package br.com.dducl.collective_coffee_marketplace.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GrupoCompraCadastroUpdateDto extends GrupoCompraDto {

    private List<String> pessoas;
}
