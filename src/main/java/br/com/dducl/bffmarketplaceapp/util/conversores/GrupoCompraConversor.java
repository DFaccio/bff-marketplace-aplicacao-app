package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraCadastroUpdateDto;
import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraFullDto;
import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.GrupoCompra;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GrupoCompraConversor implements Conversores<GrupoCompra, GrupoCompraFullDto> {

    @Resource
    private PessoaConversor pessoaConversor;

    @Override
    public GrupoCompraFullDto converte(GrupoCompra entidade) {
        GrupoCompraFullDto grupoCompraDto = new GrupoCompraFullDto();

        grupoCompraDto.setAtivo(entidade.isAtivo());
        grupoCompraDto.setNome(entidade.getNome());
        grupoCompraDto.setId(entidade.getId());

        if (entidade.getDataCriacao() != null) {
            grupoCompraDto.setDataCriacao(entidade.getDataCriacao().toString());
        }

        grupoCompraDto.setPessoas(pessoaConversor.converteEntidades(entidade.getPessoas()));

        return grupoCompraDto;
    }

    @Override
    public GrupoCompra converte(GrupoCompraFullDto dto) throws ValidationsException {
        GrupoCompra entidade = getGrupoCompra(dto.isAtivo(), dto.getNome(), dto.getId(), dto.getDataCriacao());

        entidade.setPessoas(pessoaConversor.converteDto(dto.getPessoas()));

        return entidade;
    }

    private GrupoCompra getGrupoCompra(boolean ativo, String nome, Integer id, String data) {
        GrupoCompra entidade = new GrupoCompra();

        entidade.setAtivo(ativo);
        entidade.setNome(nome);
        entidade.setId(id);

        if (data != null) {
            entidade.setDataCriacao(LocalDateTime.parse(data));
        }
        return entidade;
    }

    public GrupoCompra converte(List<PessoaDto> pessoas, GrupoCompraCadastroUpdateDto dto) {
        GrupoCompra entidade = getGrupoCompra(dto.isAtivo(), dto.getNome(), dto.getId(), dto.getDataCriacao());

        entidade.setPessoas(pessoaConversor.converteDto(pessoas));

        return entidade;
    }
}
