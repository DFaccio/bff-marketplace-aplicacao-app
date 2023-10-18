package br.com.dducl.bffmarketplaceaplicacaoapp.util.conversores;

import br.com.dducl.bffmarketplaceaplicacaoapp.dto.Dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface Conversores<T extends Serializable, D extends Dto> {

    D converte(T entidade);

    T converte(D dto);


    default List<T> converteDto(Collection<D> dtos) {
        return dtos.stream().map(this::converte).collect(Collectors.toList());
    }

    default List<D> converteEntidades(Collection<T> entidades) {
        return entidades.stream().map(this::converte).collect(Collectors.toList());
    }
}
