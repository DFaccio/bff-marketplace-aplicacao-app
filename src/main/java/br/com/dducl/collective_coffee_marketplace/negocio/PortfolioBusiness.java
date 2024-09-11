package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioResumidoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.DetalhesProdutosPortifolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio.PortfolioRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.PortfolioConversor;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
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

@Service
public class PortfolioBusiness {

    private final PortfolioConversor conversor;

    private final PortfolioRepository repository;

    private final Clock clock;

    private final ProdutoPortfolioBussiness produtoPortfolioBussiness;

    @Autowired
    public PortfolioBusiness(PortfolioConversor conversor, PortfolioRepository repository, Clock clock,
                             ProdutoPortfolioBussiness produtoPortfolioBussiness) {
        this.conversor = conversor;
        this.repository = repository;
        this.clock = clock;
        this.produtoPortfolioBussiness = produtoPortfolioBussiness;
    }

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

        if (portfolio.getDataCriacao().plusDays(60).isBefore(portfolio.getDataVigencia())) {
            throw new ValidationsException("DATA_ENCERRAMENTO_MAIOR_SESSENTA_DIAS");
        }
    }

    public PortfolioResumidoDto insert(PortfolioDto dto) throws ValidationsException, NotFoundException {
        Portfolio portfolio = conversor.converte(dto);

        portfolio.setDataCriacao(LocalDateTime.now(clock));

        portfolio.setProdutos(produtoPortfolioBussiness.normalizaAndValidaProdutos(portfolio.getProdutos()));

        validaDataPortifolio(portfolio);

        if (!List.of(StatusPortfolio.ABERTO, StatusPortfolio.FECHADO).contains(portfolio.getStatus())) {
            throw new ValidationsException("PORTIFOLIO_STATUS_CRIACAO_ERRADO");
        }

        portfolio = repository.save(portfolio);

        return conversor.converte(portfolio);
    }

    public PortfolioResumidoDto findById(Integer id) throws NotFoundException {
        return conversor.converte(getPortfolio(id));
    }

    public PortfolioResumidoDto update(PortfolioResumidoDto dto) throws NotFoundException, ValidationsException {
        Portfolio newValues = conversor.converte(dto);

        Portfolio toUpdate = getPortfolio(dto.getId());

        validaSePortfolioFinalizou(toUpdate);

        validaDataPortifolio(newValues);

        if (!List.of(StatusPortfolio.ABERTO, StatusPortfolio.FECHADO).contains(newValues.getStatus())) {
            throw new ValidationsException("PORTIFOLIO_STATUS_ALTERAR_NAO_PERMITIDO");
        }

        toUpdate.setStatus(newValues.getStatus());
        toUpdate.setDescricao(newValues.getDescricao());
        toUpdate.setDataVigencia(newValues.getDataVigencia());

        return conversor.converte(repository.save(toUpdate));
    }

    public void delete(Integer IdPortifolio) throws NotFoundException {
        repository.delete(getPortfolio(IdPortifolio));
    }

    public void update(Integer id, Integer produtoId, ProdutoPortfolioDto produtoPortfolio) throws NotFoundException, ValidationsException {
        Portfolio portfolio = getPortfolio(id);

        validaSePortfolioFinalizou(portfolio);

        produtoPortfolioBussiness.atualizaProduto(portfolio.getProdutos(), produtoId, produtoPortfolio);
    }

    private void validaSePortfolioFinalizou(Portfolio portfolio) throws ValidationsException {
        if (portfolio.getDataVigencia().isAfter(LocalDateTime.now(clock)) && StatusPortfolio.FECHADO.equals(portfolio.getStatus())) {
            throw new ValidationsException("PORTFOLIO_ENCERRADO_NAO_ALTERA");
        }
    }

    public void insertProduto(Integer id, ProdutoPortfolioDto produtoPortfolio) throws NotFoundException, ValidationsException {
        Portfolio portfolio = getPortfolio(id);

        validaSePortfolioFinalizou(portfolio);

        DetalhesProdutosPortifolio novo = produtoPortfolioBussiness.adicionaProduto(portfolio.getProdutos(), produtoPortfolio);

        portfolio.getProdutos().add(novo);

        repository.save(portfolio);
    }

    private Portfolio getPortfolio(Integer id) throws NotFoundException {
        Optional<Portfolio> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("PORTFOLIO_NAO_ENCONTRADO");
        }

        return optional.get();
    }
}