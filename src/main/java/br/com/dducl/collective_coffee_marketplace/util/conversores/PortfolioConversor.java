package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ProdutoPortfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {

    private final FornecedorConversor fornecedorConversor;

    private final ProdutoPortfolioConversor produtoPortfolioConversor;

    public PortfolioConversor(FornecedorConversor fornecedorConversor, ProdutoPortfolioConversor produtoPortfolioConversor) {
        this.fornecedorConversor = fornecedorConversor;
        this.produtoPortfolioConversor = produtoPortfolioConversor;
    }


    @Override
    public PortfolioDto converte(Portfolio entidade) {
        PortfolioDto portfolioDto = new PortfolioDto();
        portfolioDto.setId(entidade.getId());
        portfolioDto.setFornecedor(fornecedorConversor.converte(entidade.getFornecedor()));
        portfolioDto.setDescricao(entidade.getDescricao());
        portfolioDto.setDataCriacao(entidade.getDataCriacao().toString());
        portfolioDto.setDataVigencia(entidade.getDataVigencia().toString());
        portfolioDto.setStatus(entidade.getStatus());
        portfolioDto.setProdutos(produtoPortfolioConversor.converteEntidades(entidade.getProdutos().stream().toList()));

        return portfolioDto;
    }

    @Override
    public Portfolio converte(PortfolioDto dto) throws ValidationsException {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setFornecedor(fornecedorConversor.converte(dto.getFornecedor()));
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setDataVigencia(LocalDateTime.parse(dto.getDataVigencia(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        portfolio.setStatus(dto.getStatus());
        portfolio.setProdutos(new HashSet<>(produtoPortfolioConversor.converteDto(dto.getProdutos())));

        return portfolio;
    }

    public Portfolio converte(PortfolioCadastroDto dto) throws ValidationsException {
        Portfolio portfolio = new Portfolio();
        portfolio.setFornecedor(Fornecedor.builder()
                .id(dto.fornecedorId())
                .build());
        portfolio.setDescricao(dto.descricao());
        portfolio.setStatus(dto.status());
        portfolio.setDataVigencia(LocalDateTime.parse(dto.dataVigencia(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        portfolio.setProdutos(produtoPortfolioConversor.converte(dto.produtos()));

        return portfolio;
    }


}