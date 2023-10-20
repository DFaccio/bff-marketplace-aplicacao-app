package br.com.dducl.bffmarketplaceapp.util;

import br.com.dducl.bffmarketplaceapp.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ResultadoPaginado<T extends Dto> {

    private Pagination pagination;

    private List<T> dados;
}
