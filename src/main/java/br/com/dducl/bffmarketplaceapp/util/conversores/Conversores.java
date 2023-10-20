package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.Dto;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

public interface Conversores<T extends Serializable, D extends Dto> {

    D converte(T entidade);

    T converte(D dto);

    default ResultadoPaginado<D> converteEntidades(Page<T> page) {
        ResultadoPaginado<D> resultado = new ResultadoPaginado<>();

        resultado.setPagination(
                new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()))
        ;

        List<D> dada = page.get().map(this::converte).toList();

        resultado.setDados(dada);

        return resultado;
    }
}
