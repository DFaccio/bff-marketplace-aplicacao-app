package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;
import org.springframework.stereotype.Component;

@Component
public class ChavePixConversor implements Conversores<ChavesPix, ChavesPixDto> {

    @Override
    public ChavesPixDto converte(ChavesPix entidade) {
        ChavesPixDto dto = new ChavesPixDto();

        dto.setId(entidade.getId());
        dto.setChave(entidade.getChave());
        dto.setAtivo(entidade.isAtivo());

        return dto;
    }

    @Override
    public ChavesPix converte(ChavesPixDto dto) {
        ChavesPix entidade = new ChavesPix();

        entidade.setId(dto.getId());
        entidade.setChave(dto.getChave());
        entidade.setAtivo(dto.isAtivo());

        return entidade;
    }
}