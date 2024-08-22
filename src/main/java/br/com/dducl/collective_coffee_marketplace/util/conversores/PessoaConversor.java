package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaDto;
import br.com.dducl.collective_coffee_marketplace.dto.pessoa.PessoaInfoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PessoaConversor implements Conversores<Pessoa, PessoaDto> {

    @Resource
    private EnderecoConversor enderecoConversor;

    @Resource
    private ChavePixConversor chavePixConversor;

    @Override
    public PessoaDto converte(Pessoa entidade) {
        return PessoaDto.builder()
                .nome(entidade.getNome())
                .ativo(entidade.isAtivo())
                .dataCadastro(entidade.getDataCadastro().toString())
                .email(entidade.getEmail())
                .documento(entidade.getDocumento())
                .telefone(entidade.getTelefone())
                .endereco(entidade.getEndereco() == null ? null : enderecoConversor.converte(entidade.getEndereco()))
                .perfil(entidade.getPerfil())
                .chavesPix(chavePixConversor.converteEntidades(entidade.getChaves()))
                .build();
    }

    @Override
    public Pessoa converte(PessoaDto dto) throws ValidationsException {
        return Pessoa.builder()
                .nome(dto.getNome())
                .ativo(dto.isAtivo())
                .email(dto.getEmail())
                .documento(dto.getDocumento())
                .telefone(dto.getTelefone())
                .endereco(dto.getEndereco() == null ? null : enderecoConversor.converte(dto.getEndereco()))
                .chaves(chavePixConversor.converteDto(dto.getChavesPix()))
                .perfil(dto.getPerfil())
                .build();
    }

    public Pessoa converte(PessoaInfoDto dto) throws ValidationsException {
        return Pessoa.builder()
                .nome(dto.getNome())
                .ativo(dto.isAtivo())
                .email(dto.getEmail())
                .documento(dto.getDocumento())
                .telefone(dto.getTelefone())
                .chaves(chavePixConversor.converteDto(dto.getChavesPix()))
                .endereco(dto.getEndereco() == null ?
                        null :
                        enderecoConversor.converte(dto.getEndereco()))
                .perfil(dto.getPerfil())
                .build();
    }
}
