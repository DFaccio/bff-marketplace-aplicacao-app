package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.FornecedorDto;
import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.FornecedorRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.conversores.FornecedorConversor;
import br.com.dducl.bffmarketplaceapp.util.conversores.PessoaConversor;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FornecedorBusiness {

    @Resource
    private FornecedorRepository repository;

    @Resource
    private FornecedorConversor conversor;

    @Resource
    private PessoaBusiness pessoaBusiness;

    @Resource
    private PessoaConversor pessoaConversor;

    public ResultadoPaginado<FornecedorDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("id"));

        Page<Fornecedor> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public FornecedorDto findById(Integer id) throws NotFoundException {
        Optional<Fornecedor> fornecedor = repository.findById(id);

        if (fornecedor.isEmpty()) {
            throw new NotFoundException(id, "Fornecedor");
        }

        return conversor.converte(fornecedor.get());
    }

    public FornecedorDto insert(FornecedorDto dto) throws ValidationsException {
        Optional<Fornecedor> optional = repository.findFornecedorByPessoaIdentificador(dto.getInformacoes().getIdentificador());

        if (optional.isPresent()) {
            throw new ValidationsException("Fornecedor j\u00E1 cadastrado!");
        }

        PessoaDto pessoa = pessoaBusiness.insert(dto.getInformacoes());

        dto.setInformacoes(pessoa);

        Fornecedor fornecedor = conversor.converte(dto);

        return conversor.converte(repository.save(fornecedor));
    }

    public FornecedorDto findByIdentificador(String identificador) throws NotFoundException {
        Optional<Fornecedor> fornecedor = repository.findFornecedorByPessoaIdentificador(identificador);

        if (fornecedor.isEmpty()) {
            throw new NotFoundException(identificador, "Fornecedor");
        }

        return conversor.converte(fornecedor.get());
    }
}
