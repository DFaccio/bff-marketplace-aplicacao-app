package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.EnderecoDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoConversor implements Conversores<Endereco, EnderecoDto> {

    @Override
    public EnderecoDto converte(Endereco entidade) {
        EnderecoDto dto = new EnderecoDto();

        dto.setApelido(entidade.getApelido());
        dto.setBairro(entidade.getBairro());
        dto.setCep(entidade.getCep());
        dto.setCidade(entidade.getCidade());
        dto.setComplemento(entidade.getComplemento());
        dto.setEstado(entidade.getEstado());
        dto.setLogradouro(entidade.getLogradouro());
        dto.setNumero(entidade.getNumero());
        dto.setId(entidade.getId());

        return dto;
    }

    @Override
    public Endereco converte(EnderecoDto dto) {
        Endereco entidade = new Endereco();

        entidade.setApelido(dto.getApelido());
        entidade.setBairro(dto.getBairro());
        entidade.setCep(dto.getCep());
        entidade.setCidade(dto.getCidade());
        entidade.setComplemento(dto.getComplemento());
        entidade.setEstado(dto.getEstado());
        entidade.setLogradouro(dto.getLogradouro());
        entidade.setNumero(dto.getNumero());
        entidade.setId(dto.getId());

        return entidade;
    }
}
