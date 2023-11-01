package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.FornecedorRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PortfolioRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.conversores.FornecedorConversor;
import br.com.dducl.bffmarketplaceapp.util.conversores.PortfolioConversor;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PortfolioBusiness {

    @Resource
    private PortfolioConversor conversor;

    @Resource
    private PortfolioRepository repository;

    @Resource
    private FornecedorRepository fornecedorRepository;

    @Resource
    private FornecedorConversor fornecedorConversor;

    public ResultadoPaginado<PortfolioDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("descricao"));

        Page<Portfolio> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public PortfolioDto insert(PortfolioDto dto) throws ValidationsException,NotFoundException {
        Portfolio portfolio = conversor.converte(dto);

        Optional<Fornecedor> fornecedor = fornecedorRepository.findFornecedorByPessoaIdentificador(dto.getFornecedor().getInformacoes().getIdentificador());

        if (fornecedor.isEmpty()){
            throw new NotFoundException(dto.getId(), "Fornecedor");
        }

        portfolio.setFornecedor(fornecedor.get());

        Optional<Portfolio> portfolioCriado = repository.findPortfolioByFornecedorAndDescricao(portfolio.getFornecedor(), portfolio.getDescricao());

        if (portfolioCriado.isPresent()) {
            throw new ValidationsException("Portfólio já cadastrado para este fornecedor e mesma descrição, por favor verifique!");
        }

        portfolio.setDataCriacao(LocalDateTime.now());

        portfolio.getProdutos().forEach(produto -> produto.setDataCriacao(LocalDateTime.now()));
        Portfolio portfolioSalvo = repository.save(portfolio);

        dto = conversor.converte(portfolioSalvo);
        dto.setFornecedor( fornecedorConversor.converte(portfolio.getFornecedor()));

        return dto;
    }

    public PortfolioDto findPortfolioById(int id) {
        var portfolio = (repository.getReferenceById(id));
        return conversor.converte(portfolio);
    }

    public PortfolioDto update(PortfolioDto dto) throws NotFoundException {
        Optional<Portfolio> portfolio = repository.findPortfolioById(dto.getId());

        if (portfolio.isEmpty()) {
            throw new NotFoundException(dto.getId(), "Portfolio");
        }

        Portfolio portfolioToUpdate = portfolio.get();
        portfolioToUpdate.setStatus(dto.getStatus());
        portfolioToUpdate.setDescricao(dto.getDescricao());
        portfolioToUpdate.setDataVigencia(dto.getDataVigencia());

        return conversor.converte(repository.save(portfolioToUpdate));
    }

    public void delete(Integer IdPortifolio) throws NotFoundException {
        Optional<Portfolio> portfolioDeletar = repository.findPortfolioById(IdPortifolio);

        if (portfolioDeletar.isEmpty()){
            throw new NotFoundException(IdPortifolio, "Portfolio");
        }

        repository.deleteById(IdPortifolio);
    }
}