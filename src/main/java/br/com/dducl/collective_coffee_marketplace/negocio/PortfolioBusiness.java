package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioResumidoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.DetalhesProdutosPortifolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.ProdutoPortfolioRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.portfolio.PortfolioRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.produto.ProdutoRepository;
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

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioBusiness {

    @Resource
    private PortfolioConversor conversor;

    @Resource
    private PortfolioRepository repository;

    @Resource
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPortfolioRepository produtoPortfolioRepository;

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

    public PortfolioResumidoDto insert(PortfolioDto dto) throws ValidationsException, NotFoundException {
        Portfolio portfolio = conversor.converte(dto);

        portfolio.setDataCriacao(LocalDateTime.now(clock));

        portfolio.setProdutos(normalizeProdutosPortifolio(portfolio.getProdutos()));

        validaDataPortifolio(portfolio);

        if (!List.of(StatusPortfolio.ABERTO, StatusPortfolio.FECHADO).contains(portfolio.getStatus())) {
            throw new ValidationsException("PORTIFOLIO_STATUS_CRIACAO_ERRADO");
        }

        portfolio = repository.save(portfolio);

        return conversor.converte(portfolio);
    }

    // TODO está com problema na criação do item
    // TODO falta validar o restante dos restos do metodos
    private Set<DetalhesProdutosPortifolio> normalizeProdutosPortifolio(Set<DetalhesProdutosPortifolio> produtoPortfolios) throws ValidationsException {
        Set<Integer> produtosId = produtoPortfolios.stream()
                .map(produtoPortfolio -> produtoPortfolio.getProduto().getId())
                .collect(Collectors.toSet());

        Map<Integer, Produto> produtosMap = produtoRepository.findAllByIdIn(produtosId).stream()
                .collect(Collectors.toMap(Produto::getId, produto -> produto));

        if (produtosMap.isEmpty()) {
            throw new ValidationsException("PORTIFOLIO_SEM_PRODUTO_CRIADO");
        }

        Set<DetalhesProdutosPortifolio> produtos = new HashSet<>(produtosMap.size());

        produtoPortfolios.forEach(produtoPortfolio -> {
            if (produtosMap.containsKey(produtoPortfolio.getProduto().getId())) {
                produtoPortfolio.setProduto(produtosMap.get(produtoPortfolio.getId()));

                if (BigDecimal.ZERO.equals(produtoPortfolio.getValor())) {
                    produtoPortfolio.setValor(produtoPortfolio.getProduto().getValor());
                }

                produtos.add(produtoPortfolio);
            }
        });

        return produtos;
    }

    public PortfolioResumidoDto findById(Integer id) throws NotFoundException {
        Optional<Portfolio> portfolioOptional = repository.findById(id);

        if (portfolioOptional.isEmpty()) {
            throw new NotFoundException("Portfólio");
        }

        return conversor.converte(portfolioOptional.get());
    }

    public PortfolioResumidoDto update(PortfolioResumidoDto dto) throws NotFoundException, ValidationsException {
        Portfolio newValues = conversor.converte(dto);
        Optional<Portfolio> portfolio = repository.findById(dto.getId());

        if (portfolio.isEmpty()) {
            throw new NotFoundException("Portfólio");
        }

        Portfolio toUpdate = portfolio.get();

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
        Optional<Portfolio> optional = repository.findById(IdPortifolio);

        if (optional.isEmpty()) {
            throw new NotFoundException("Portfolio");
        }

        repository.delete(optional.get());
    }

    public void update(Integer id, Integer produtoId, ProdutoPortfolioDto produtoPortfolio) throws NotFoundException, ValidationsException {
        Optional<Portfolio> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Portfolio");
        }

        Portfolio portfolio = optional.get();

        validaSePortfolioFinalizou(portfolio);

        Optional<DetalhesProdutosPortifolio> optionaltoUpdate = optional.get().getProdutos().stream()
                .filter(produto -> produto.getId().equals(produtoId))
                .findAny();

        if (optionaltoUpdate.isEmpty()) {
            throw new NotFoundException("Item do portfólio");
        }

        DetalhesProdutosPortifolio toUpdate = optionaltoUpdate.get();
        toUpdate.setValor(produtoPortfolio.getValor());
        toUpdate.setDesconto(produtoPortfolio.getDesconto());
        toUpdate.setTipoDesconto(produtoPortfolio.getTipoDesconto());

        produtoPortfolioRepository.save(toUpdate);
    }

    private void validaSePortfolioFinalizou(Portfolio portfolio) throws ValidationsException {
        if (portfolio.getDataVigencia().isAfter(LocalDateTime.now(clock)) && StatusPortfolio.FECHADO.equals(portfolio.getStatus())) {
            throw new ValidationsException("PORTFOLIO_ENCERRADO_NAO_ALTERA");
        }
    }
}