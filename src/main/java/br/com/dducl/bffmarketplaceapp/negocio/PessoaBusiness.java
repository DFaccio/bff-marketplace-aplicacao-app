package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.ChavesPixRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PessoaRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.PessoaConversor;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaBusiness {

    @Resource
    private PessoaConversor conversor;

    @Resource
    private PessoaRepository repository;

    @Resource
    private ChavesPixRepository chavesPixRepository;

    public ResultadoPaginado<PessoaDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("identificador"));

        Page<Pessoa> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public PessoaDto insert(PessoaDto dto) throws ValidacoesException {
        Pessoa pessoa = conversor.converte(dto);

        Optional<Pessoa> jaCriada = repository.findPessoaByIdentificadorEquals(pessoa.getIdentificador());

        if (jaCriada.isPresent()) {
            throw new ValidacoesException("Pessoa j\u00E1 cadastrada!!");
        }

        List<ChavesPix> chaves = dto.getChavesAtivas().stream().map(chave -> {
            ChavesPix nova = new ChavesPix();
            nova.setChave(chave);
            nova.setAtivo(true);
            nova.setDataCadastro(LocalDate.now());

            return nova;
        }).toList();

        chaves = chavesPixRepository.saveAll(chaves);

        pessoa.setChaves(chaves);
        pessoa.setDataCadastro(LocalDateTime.now());

        dto = conversor.converte(repository.save(pessoa));

        return dto;
    }

    public PessoaDto findByIdentificador(String identificador) throws ValidacoesException {
        Optional<Pessoa> pessoa = repository.findPessoaByIdentificadorEquals(identificador);

        if (pessoa.isEmpty()) {
            throw new ValidacoesException("Pessoa n\u00E3o identificada!");
        }

        return conversor.converte(pessoa.get());
    }

    public void update(PessoaDto dto) throws ValidacoesException {
        Optional<Pessoa> pessoa = repository.findPessoaByIdentificadorEquals(dto.getIdentificador());

        if (pessoa.isEmpty()) {
            throw new ValidacoesException("Pessoa informada para atualiza\u00E7\u00E3o n\u00E3o foi encontrada!");
        }

        Pessoa atualizar = pessoa.get();

        atualizar.setTelefone(dto.getTelefone());
        atualizar.setEmail(dto.getEmail());
        atualizar.setAtivo(dto.isAtivo());
        atualizar.setNome(dto.getNome());

        repository.save(atualizar);
    }
}
