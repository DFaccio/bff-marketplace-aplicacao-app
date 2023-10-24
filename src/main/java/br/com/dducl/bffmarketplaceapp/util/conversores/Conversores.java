package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.Dto;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Conversores<T extends Serializable, D extends Dto> {

    D converte(T entidade);

    T converte(D dto) throws ValidationsException;

    default ResultadoPaginado<D> converteEntidades(Page<T> page) {
        ResultadoPaginado<D> resultado = new ResultadoPaginado<>();

        resultado.setPagination(
                new Pagination(page.getNumber(), page.getSize(), page.getTotalPages()))
        ;

        List<D> dada = page.get().map(this::converte).toList();

        resultado.setDados(dada);

        return resultado;
    }

    default List<D> converteEntidades(List<T> entidades) {
        if (entidades == null) {
            return Collections.emptyList();
        }

        return entidades.stream().map(this::converte).toList();
    }

    default List<T> converteDto(List<D> dto) {
        if (dto == null) {
            return Collections.emptyList();
        }

        List<T> entidades = new ArrayList<>();

        dto.forEach(item -> {
            try {
                entidades.add(converte(item));
            } catch (ValidationsException e) {
                throw new RuntimeException(e);
            }
        });

        return entidades;
    }
}
