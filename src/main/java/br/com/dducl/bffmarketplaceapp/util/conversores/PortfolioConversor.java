package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.FornecedorDto;
import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {
    @Resource
    private FornecedorConversor fornecedorConversor;

    @Override
    public PortfolioDto converte(Portfolio entidade) {
        PortfolioDto dto = new PortfolioDto();

        FornecedorDto fornecedorDto = fornecedorConversor.converte(entidade.getFornecedor());
        dto.setFornecedor(fornecedorDto);

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

        portfolio.setId(dto.getId());
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setStatus(dto.getStatus());
        portfolio.setDataVigencia(dto.getDataVigencia());
        portfolio.setFornecedor(fornecedorConversor.converte(dto.getFornecedor()));

        return portfolio;
    }
}
