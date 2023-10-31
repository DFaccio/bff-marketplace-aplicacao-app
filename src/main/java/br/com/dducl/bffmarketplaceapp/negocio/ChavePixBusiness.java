package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.ChavesPixDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.ChavesPixRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PessoaRepository;
import br.com.dducl.bffmarketplaceapp.util.conversores.ChavePixConversor;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChavePixBusiness {

    @Resource
    private ChavesPixRepository repository;

    @Resource
    private PessoaRepository pessoaRepository;

    @Resource
    private ChavePixConversor conversor;

    public void insert(String identificador, ChavesPixDto chavesPixDto) throws NotFoundException, ValidationsException, NoSuchFieldException {
        Optional<Pessoa> optional = pessoaRepository.findPessoaByIdentificadorEquals(identificador);

        if (optional.isEmpty()) {
            throw new NotFoundException(identificador, "Pessoa");
        }

        ChavesPix jaCadastrada = repository.findChavePixByPessoaAndChave(identificador, chavesPixDto.getChave());

        if (jaCadastrada != null) {
            throw new ValidationsException("Chave já cadastrada");
        }

        Pessoa pessoa = optional.get();

        ChavesPix chave = conversor.converte(chavesPixDto);
        chave.setDataCadastro(LocalDate.now());

        if (pessoa.getChaves() == null) {
            pessoa.setChaves(new ArrayList<>());
        }

        pessoa.getChaves().add(chave);

        pessoaRepository.save(pessoa);
    }

    public void update(String chave, ChavesPixDto chavesPixDto, String identificador) throws ValidationsException {
        Optional<Pessoa> optional = pessoaRepository.findPessoaByIdentificadorEqualsAndChaves_Chave(identificador, chave);

        if (optional.isEmpty()) {
            throw new ValidationsException(String.format("%s chave não encontrada!", chave));
        }

        Pessoa pessoa = optional.get();
        pessoa.getChaves().forEach(key -> {
            if (key.getChave().equals(chave)) {
                key.setChave(chavesPixDto.getChave());
                key.setAtivo(chavesPixDto.isAtivo());
            }
        });

        pessoaRepository.save(pessoa);
    }
}
