package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.FornecedorDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.FornecedorRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.FornecedorConversor;
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

    public ResultadoPaginado<FornecedorDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("id"));

        Page<Fornecedor> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public FornecedorDto findById(Integer id) throws ValidacoesException {
        Optional<Fornecedor> fornecedor = repository.findById(id);

        if (fornecedor.isEmpty()) {
            throw new ValidacoesException("Fornecedor n\u00E3o encontrado!");
        }

        return conversor.converte(fornecedor.get());
    }

    public void insert(FornecedorDto dto) throws ValidacoesException {
        Optional<Fornecedor> optional = repository.findFornecedorByPessoaIdentificador(dto.getInformacoes().getIdentificador());

        if (optional.isPresent()) {
            throw new ValidacoesException("Fornecedor j\u00E1 cadastrado!");
        }

        pessoaBusiness.insert(dto.getInformacoes());

        Fornecedor fornecedor = conversor.converte(dto);

        repository.save(fornecedor);
    }

    public FornecedorDto findByIdentificador(String identificador) throws ValidacoesException {
        Optional<Fornecedor> fornecedor = repository.findFornecedorByPessoaIdentificador(identificador);

        if (fornecedor.isEmpty()) {
            throw new ValidacoesException("Fornecedor n\u00E3o encontrado!");
        }

        return conversor.converte(fornecedor.get());
    }
}
