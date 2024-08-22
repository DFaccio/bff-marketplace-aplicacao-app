package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.EnderecoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Endereco;
import org.springframework.stereotype.Component;

@Component
public class EnderecoConversor implements Conversores<Endereco, EnderecoDto> {

    @Override
    public EnderecoDto converte(Endereco entidade) {
        EnderecoDto dto = EnderecoDto.builder()
                .apelido(entidade.getApelido())
                .bairro(entidade.getBairro())
                .cep(entidade.getCep())
                .cidade(entidade.getCidade())
                .complemento(entidade.getComplemento())
                .estado(entidade.getEstado())
                .logradouro(entidade.getLogradouro())
                .numero(entidade.getNumero())
                .build();

        dto.setId(entidade.getId());

        return dto;
    }

    @Override
    public Endereco converte(EnderecoDto dto) {
        return Endereco.builder()
                .apelido(dto.getApelido())
                .bairro(dto.getBairro())
                .cep(dto.getCep())
                .cidade(dto.getCidade())
                .complemento(dto.getComplemento())
                .estado(dto.getEstado())
                .logradouro(dto.getLogradouro())
                .numero(dto.getNumero())
                .id(dto.getId())
                .build();
    }
}
