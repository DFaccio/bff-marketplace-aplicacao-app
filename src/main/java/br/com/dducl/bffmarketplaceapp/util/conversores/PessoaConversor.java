package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PessoaConversor implements Conversores<Pessoa, PessoaDto> {

    @Override
    public PessoaDto converte(Pessoa entidade) {
        PessoaDto dto = new PessoaDto();

        dto.setNome(entidade.getNome());
        dto.setAtivo(entidade.isAtivo());
        dto.setDataCadastro(entidade.getDataCadastro().toString());
        dto.setEmail(entidade.getEmail());
        dto.setIdentificador(entidade.getIdentificador());
        dto.setTelefone(entidade.getTelefone());

        List<String> chaves = entidade.getChaves()
                .stream()
                .filter(ChavesPix::isAtivo)
                .map(ChavesPix::getChave)
                .toList();

        dto.setChavesAtivas(chaves);

        return dto;
    }

    @Override
    public Pessoa converte(PessoaDto dto) {
        Pessoa pessoa = new Pessoa();

        pessoa.setAtivo(dto.isAtivo());
        pessoa.setEmail(dto.getEmail());
        pessoa.setIdentificador(dto.getIdentificador());
        pessoa.setNome(dto.getNome());
        pessoa.setTelefone(dto.getTelefone());

        return pessoa;
    }

}
