package br.com.dducl.bffmarketplaceapp.util.conversores;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioProdutosDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.PortfolioProdutos;
import org.springframework.stereotype.Component;

@Component
public class PortfolioProdutosConversor implements Conversores<PortfolioProdutos, PortfolioProdutosDto> {
        ProdutoConversor produtoConversor;
        PortfolioConversor portfolioConversor;
        @Override
        public PortfolioProdutosDto converte(PortfolioProdutos entidade) {
            PortfolioProdutosDto dto = new PortfolioProdutosDto();

            dto.setId(entidade.getId());
            dto.setProduto(produtoConversor.converte(entidade.getProduto()));
            dto.setPortfolio(portfolioConversor.converte(entidade.getPortfolio()));
            dto.setDataCriacao(entidade.getDataCriacao());

            return dto;
        }

        @Override
        public PortfolioProdutos converte(PortfolioProdutosDto dto) {
            PortfolioProdutos portfolioProdutos = new PortfolioProdutos();

            portfolioProdutos.setId(dto.getId());
            portfolioProdutos.setProduto(produtoConversor.converte(dto.getProduto()));
            portfolioProdutos.setPortfolio(portfolioConversor.converte(dto.getPortfolio()));
            portfolioProdutos.setDataCriacao(dto.getDataCriacao());

            return portfolioProdutos;
        }

}
