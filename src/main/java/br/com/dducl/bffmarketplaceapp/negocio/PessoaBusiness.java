package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Pessoa;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PessoaRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.conversores.PessoaConversor;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PessoaBusiness {

    @Resource
    private PessoaConversor conversor;

    @Resource
    private PessoaRepository repository;

    public ResultadoPaginado<PessoaDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("identificador"));

        Page<Pessoa> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public PessoaDto insert(PessoaDto dto) throws ValidationsException {
        Pessoa pessoa = conversor.converte(dto);

        Optional<Pessoa> jaCriada = repository.findPessoaByIdentificadorEquals(pessoa.getIdentificador());

        if (jaCriada.isPresent()) {
            throw new ValidationsException("Pessoa j\u00E1 cadastrada!!");
        }

        pessoa.getChaves().forEach(chave -> {
            chave.setDataCadastro(LocalDate.now());
            chave.setId(null);
        });
        pessoa.setDataCadastro(LocalDateTime.now());

        pessoa = repository.save(pessoa);

        return conversor.converte(pessoa);
    }

    public PessoaDto findByIdentificador(String identificador) throws NotFoundException {
        Optional<Pessoa> pessoa = repository.findPessoaByIdentificadorEquals(identificador);

        if (pessoa.isEmpty()) {
            throw new NotFoundException(identificador, "Pessoa");
        }

        return conversor.converte(pessoa.get());
    }

    public PessoaDto update(PessoaDto dto) throws ValidationsException {
        Optional<Pessoa> optional = repository.findPessoaByIdentificadorEquals(dto.getIdentificador());

        if (optional.isEmpty()) {
            throw new ValidationsException("Pessoa informada para atualiza\u00E7\u00E3o n\u00E3o foi encontrada!");
        }

        Pessoa atualizar = optional.get();

        Pessoa pessoa = conversor.converte(dto);

        atualizar.setTelefone(pessoa.getTelefone());
        atualizar.setEmail(pessoa.getEmail());
        atualizar.setAtivo(pessoa.isAtivo());
        atualizar.setNome(pessoa.getNome());

        if (pessoa.getEndereco() != null) {
            atualizar.setEndereco(pessoa.getEndereco());
        }

        pessoa = repository.save(atualizar);

        return conversor.converte(pessoa);
    }
}
