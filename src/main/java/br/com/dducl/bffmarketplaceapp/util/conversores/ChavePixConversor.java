package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.ChavesPixDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
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