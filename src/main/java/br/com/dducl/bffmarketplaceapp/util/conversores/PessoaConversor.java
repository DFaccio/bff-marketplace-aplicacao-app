package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.ChavesPixDto;
import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PessoaConversor implements Conversores<Pessoa, PessoaDto> {

    @Resource
    private EnderecoConversor enderecoConversor;

    @Resource
    private ChavePixConversor chavePixConversor;

    @Override
    public PessoaDto converte(Pessoa entidade) {
        PessoaDto dto = new PessoaDto();

        dto.setNome(entidade.getNome());
        dto.setAtivo(entidade.isAtivo());
        dto.setDataCadastro(entidade.getDataCadastro().toString());
        dto.setEmail(entidade.getEmail());
        dto.setIdentificador(entidade.getIdentificador());
        dto.setTelefone(entidade.getTelefone());

        if (entidade.getEndereco() != null) {
            dto.setEndereco(enderecoConversor.converte(entidade.getEndereco()));
        }

        List<ChavesPixDto> chaves = chavePixConversor.converteEntidades(entidade.getChaves());
        dto.setChavesPix(chaves);

        return dto;
    }

    @Override
    public Pessoa converte(PessoaDto dto) throws ValidationsException {
        Pessoa pessoa = new Pessoa();

        pessoa.setAtivo(dto.isAtivo());
        pessoa.setEmail(dto.getEmail());
        pessoa.setIdentificador(dto.getIdentificador());
        pessoa.setNome(dto.getNome());
        pessoa.setTelefone(dto.getTelefone());

        if (dto.getDataCadastro() != null) {
            pessoa.setDataCadastro(LocalDateTime.parse(dto.getDataCadastro()));
        }
        if (dto.getEndereco() != null) {
            pessoa.setEndereco(enderecoConversor.converte(dto.getEndereco()));
        }

        List<ChavesPix> chavesPixes = chavePixConversor.converteDto(dto.getChavesPix());
        pessoa.setChaves(chavesPixes);

        return pessoa;
    }

}
