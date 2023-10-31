package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.*;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Produto;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PortfolioConversor implements Conversores<Portfolio, PortfolioDto> {
    @Resource
    private FornecedorConversor fornecedorConversor;

    @Resource
    private ProdutoConversor produtoConversor;

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

        List<ProdutoDto> produtos = produtoConversor.converteEntidades(entidade.getPortfolioProdutos());
        dto.setPortfolioProdutos(produtos);

        return dto;
    }

    @Override
    public Portfolio converte(PortfolioDto dto) throws ValidationsException  {
        Portfolio portfolio = new Portfolio();

        portfolio.setId(dto.getId());
        portfolio.setDescricao(dto.getDescricao());
        portfolio.setStatus(dto.getStatus());
        portfolio.setDataVigencia(dto.getDataVigencia());
        portfolio.setFornecedor(fornecedorConversor.converte(dto.getFornecedor()));

        List<Produto> produtos = produtoConversor.converteDto(dto.getPortfolioProdutos());
        portfolio.setPortfolioProdutos(produtos);

        return portfolio;
    }
}