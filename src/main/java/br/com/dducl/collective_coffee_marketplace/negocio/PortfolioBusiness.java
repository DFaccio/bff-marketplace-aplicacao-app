package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioResumidoDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ProdutoPortfolio;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.ProdutoRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor.FornecedorRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio.PortfolioRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.PortfolioConversor;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PortfolioBusiness {

    @Resource
    private PortfolioConversor conversor;

    @Resource
    private PortfolioRepository repository;

    @Resource
    private FornecedorRepository fornecedorRepository;

    @Resource
    private ProdutoRepository produtoRepository;

    @Autowired
    private Clock clock;

    public ResultadoPaginado<PortfolioDto> findAll(String documento, Integer idFornecedor, String dataEncerramento,
                                                   StatusPortfolio status, String produto, Pagination page) throws ValidationsException {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("dataVigencia"));

        Page<Portfolio> pagina = repository.findAll(documento, idFornecedor, getDate(dataEncerramento), status, produto, pageable);

        return conversor.converteEntidades(pagina);
    }

    private LocalDateTime getDate(String dateTime) throws ValidationsException {
        try {
            if (dateTime == null) {
                return LocalDateTime.now().plusDays(30);
            }

            return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException exception) {
            throw new ValidationsException("FORMATO_DATA_INVALIDO");
        }
    }

    private void validaDataPortifolio(Portfolio portfolio) throws ValidationsException {
        if (portfolio.getDataVigencia().isBefore(portfolio.getDataCriacao())) {
            throw new ValidationsException("DATA_ENCERRAMENTO_PORTIFOLIO_MENOR_QUE_DATA_CRIACAO");
        }

        if (portfolio.getDataCriacao().plusDays(30).isBefore(portfolio.getDataVigencia())) {
            throw new ValidationsException("DATA_ENCERRAMENTO_MAIOR_SESSENTA_DIAS");
        }
    }

    public PortfolioResumidoDto insert(PortfolioCadastroDto dto) throws ValidationsException, NotFoundException {
        Portfolio portfolio = conversor.converte(dto);

        portfolio.setDataCriacao(LocalDateTime.now(clock));

        validaDataPortifolio(portfolio);

        portfolio.setFornecedor(getFornecedor(portfolio.getFornecedor().getId()));

        portfolio.setProdutos(normalizeProdutosPortifolio(portfolio.getProdutos()));



       /* Optional<Fornecedor> fornecedor = fornecedorRepository.findFornecedorByPessoaDocumento(dto.getFornecedor().getInformacoes().getIdentificador());

        if (fornecedor.isEmpty()) {
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
        *//*dto.setFornecedor(fornecedorConversor.converte(portfolio.getFornecedor()));*//*

         */
        return dto;
    }

    private Set<ProdutoPortfolio> normalizeProdutosPortifolio(Set<ProdutoPortfolio> produtoPortfolios) {
        Set<Integer> produtosId = produtoPortfolios.stream()
                .map(produtoPortfolio -> produtoPortfolio.getProduto().getId())
                .collect(Collectors.toSet());
        List<Produto> produtos = produtoRepository.findAll(produtosId); // TODO IMPLEMENTAR METODO

    }

    private Fornecedor getFornecedor(Integer id) throws NotFoundException {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findById(id);

        if (optionalFornecedor.isEmpty()) {
            throw new NotFoundException("NAO_ENCONTRADO", "Fornecedor");
        }

        return optionalFornecedor.get();
    }

    // TODO daqui para baixo a validação está em andamento
    public PortfolioResumidoDto findPortfolioById(int id) {
        var portfolio = (repository.getReferenceById(id));
        return conversor.converte(portfolio);
    }

    public PortfolioResumidoDto update(PortfolioResumidoDto dto) throws NotFoundException {
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

        if (portfolioDeletar.isEmpty()) {
            throw new NotFoundException(IdPortifolio, "Portfolio");
        }

        repository.deleteById(IdPortifolio);
    }

    public PortfolioDto update(Integer id, ProdutoPortfolioDto produtoPortfolio) {
        return null;
    }
}