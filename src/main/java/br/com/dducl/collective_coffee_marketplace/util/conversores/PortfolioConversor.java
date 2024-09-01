package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioResumidoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Portfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {

    private final ProdutoPortfolioConversor produtoPortfolioConversor;

    public PortfolioConversor(ProdutoPortfolioConversor produtoPortfolioConversor) {
        this.produtoPortfolioConversor = produtoPortfolioConversor;
    }


    @Override
    public PortfolioDto converte(Portfolio entidade) {
        PortfolioDto portfolioDto = new PortfolioDto();
        portfolioDto.setId(entidade.getId());
        portfolioDto.setDescricao(entidade.getDescricao());
        portfolioDto.setDataCriacao(entidade.getDataCriacao().toString());
        portfolioDto.setDataVigencia(entidade.getDataVigencia().toString());
        portfolioDto.setStatus(entidade.getStatus());
        portfolioDto.setProdutos(produtoPortfolioConversor.converteEntidades(entidade.getProdutos().stream().toList()));

        return portfolioDto;
    }

    @Override
    public Portfolio converte(PortfolioDto dto) throws ValidationsException {
        Portfolio portfolio = getPortfolio(dto);
        portfolio.setProdutos(new HashSet<>(produtoPortfolioConversor.converteDto(dto.getProdutos())));

        return portfolio;
    }

    public Portfolio converte(PortfolioResumidoDto dto) {
        return getPortfolio(dto);
    }

    private static Portfolio getPortfolio(PortfolioResumidoDto dto) {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(dto.getId());
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setDataVigencia(LocalDateTime.parse(dto.getDataVigencia(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        portfolio.setStatus(dto.getStatus());

        return portfolio;
    }
}