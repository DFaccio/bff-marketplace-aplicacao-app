package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import org.springframework.stereotype.Component;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {
    @Override
    public PortfolioDto converte(Portfolio entidade) {
        PortfolioDto dto = new PortfolioDto();

        dto.setId(entidade.getId());
        dto.setDescricao(entidade.getDescricao());
        dto.setStatus(entidade.getStatus());
        dto.setDataVigencia(entidade.getDataVigencia());
        dto.setDataCriacao(entidade.getDataCriacao());

        return dto;
    }

    @Override
    public Portfolio converte(PortfolioDto dto) {
        Portfolio portfolio = new Portfolio();

        System.out.println("Fornecedor da entidade");
        System.out.println(dto.getFornecedor().getId());

        portfolio.setId(dto.getId());
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setStatus(dto.getStatus());
        portfolio.setDataVigencia(dto.getDataVigencia());

        return portfolio;
    }
}
