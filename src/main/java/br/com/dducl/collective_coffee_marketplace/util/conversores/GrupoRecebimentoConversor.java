package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoRecebimentoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoRecebimento;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class GrupoRecebimentoConversor implements Conversores<GrupoRecebimento, GrupoRecebimentoDto> {

    private final EnderecoConversor enderecoConversor;

    public GrupoRecebimentoConversor(EnderecoConversor enderecoConversor) {
        this.enderecoConversor = enderecoConversor;
    }

    @Override
    public GrupoRecebimentoDto converte(GrupoRecebimento entidade) {
        return GrupoRecebimentoDto.builder()
                .endereco(enderecoConversor.converte(entidade.getEndereco()))
                .ativo(entidade.isAtivo())
                .email(entidade.getEmail())
                .dataCadastro(entidade.getDataCriacao().toString())
                .nome(entidade.getNome())
                .telefone(entidade.getTelefone())
                .id(entidade.getId())
                .build();
    }

    @Override
    public GrupoRecebimento converte(GrupoRecebimentoDto dto) throws ValidationsException {
        return GrupoRecebimento.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .ativo(dto.isAtivo())
                .endereco(enderecoConversor.converte(dto.getEndereco()))
                .build();
    }
}
