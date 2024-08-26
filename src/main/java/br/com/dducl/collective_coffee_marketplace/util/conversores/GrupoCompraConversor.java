package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraFullDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.GrupoCompra;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class GrupoCompraConversor implements Conversores<GrupoCompra, GrupoCompraFullDto> {

    @Resource
    private PessoaConversor pessoaConversor;

    @Override
    public GrupoCompraFullDto converte(GrupoCompra entidade) {
        GrupoCompraFullDto grupoCompraDto = new GrupoCompraFullDto();

        grupoCompraDto.setId(entidade.getId());
        grupoCompraDto.setNome(entidade.getNome());
        grupoCompraDto.setAtivo(entidade.isAtivo());
        grupoCompraDto.setDataCriacao(entidade.getDataCriacao().toString());
        grupoCompraDto.setPessoas(pessoaConversor.converteEntidades(entidade.getPessoas()));
        grupoCompraDto.setAdministrador(pessoaConversor.converte(entidade.getAdministrador()));

        return grupoCompraDto;
    }

    @Override
    public GrupoCompra converte(GrupoCompraFullDto dto) throws ValidationsException {
        GrupoCompra grupoCompra = new GrupoCompra();

        grupoCompra.setId(dto.getId());
        grupoCompra.setNome(dto.getNome());
        grupoCompra.setAtivo(dto.isAtivo());
        grupoCompra.setAdministrador(pessoaConversor.converte(dto.getAdministrador()));
        grupoCompra.setPessoas(pessoaConversor.converteDto(dto.getPessoas()));

        return grupoCompra;
    }
}
