package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ChavesPix;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Pessoa;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.chavespix.ChavesPixRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.pessoa.PessoaRepository;
import br.com.dducl.collective_coffee_marketplace.util.conversores.ChavePixConversor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChavePixBusiness {

    @Resource
    private ChavesPixRepository repository;

    @Resource
    private PessoaRepository pessoaRepository;

    @Resource
    private ChavePixConversor conversor;

    @Autowired
    private Clock clock;

    public void insert(String documento, List<ChavesPixDto> chavesPixDto) throws NotFoundException, ValidationsException, NoSuchFieldException {
        Optional<Pessoa> optional = pessoaRepository.findPessoaByDocumentoEquals(documento);

        if (optional.isEmpty()) {
            throw new NotFoundException("PESSOA_DOCUMENTO_NAO_ENCONTRADO");
        }

        Pessoa pessoa = optional.get();

        validaChaveAndUpdate(pessoa, chavesPixDto);

        pessoaRepository.save(pessoa);
    }

    private void validaChaveAndUpdate(Pessoa pessoa, List<ChavesPixDto> chavesDto) {
        List<ChavesPix> novasChaves = chavesDto.stream()
                .collect(
                        Collectors.toMap(
                                ChavesPixDto::getChave,
                                pix -> new ChavesPix(pix.getChave(), pix.isAtivo(), LocalDate.now(clock)),
                                (existing, replacement) -> existing
                        ))
                .values()
                .stream()
                .toList();

        if (pessoa.getChaves() == null || pessoa.getChaves().isEmpty()) {
            pessoa.setChaves(novasChaves);
        } else {
            Set<String> chavesSaved = pessoa.getChaves().stream()
                    .map(ChavesPix::getChave)
                    .collect(Collectors.toSet());

            List<ChavesPix> toAdd = novasChaves.stream()
                    .filter(chave -> !chavesSaved.contains(chave.getChave()))
                    .toList();

            pessoa.getChaves().addAll(toAdd);
        }
    }

    public ChavesPixDto update(String documentoPessoal, String chave, ChavesPixDto chavesPixDto) throws NotFoundException, ValidationsException {
        Optional<ChavesPix> chaveOptional = repository.findByChaveAndDocumentoPessoa(chave, documentoPessoal);

        if (chaveOptional.isEmpty()) {
            throw new NotFoundException("CHAVE_NAO_ENCONTRADA_DOCUMENTO_PESSOA");
        }

        Optional<ChavesPix> chaveOptionalNewChave = repository.findByChaveAndDocumentoPessoa(chavesPixDto.getChave(), documentoPessoal);

        if (chaveOptionalNewChave.isPresent()) {
            throw new ValidationsException("CHAVE_JA_CADASTRADA");
        }

        ChavesPix chavesPix = chaveOptional.get();
        chavesPix.setChave(chavesPixDto.getChave());
        chavesPix.setAtivo(chavesPixDto.isAtivo());
        chavesPix.setDataCadastro(LocalDate.now(clock));

        chavesPix = repository.save(chavesPix);

        return conversor.converte(chavesPix);
    }
}
