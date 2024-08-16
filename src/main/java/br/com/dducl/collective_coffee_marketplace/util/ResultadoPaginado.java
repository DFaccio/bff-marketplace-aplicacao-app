package br.com.dducl.collective_coffee_marketplace.util;

import br.com.dducl.collective_coffee_marketplace.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResultadoPaginado<T extends Dto> {

    private Pagination pagination;

    private List<T> dados;
}
