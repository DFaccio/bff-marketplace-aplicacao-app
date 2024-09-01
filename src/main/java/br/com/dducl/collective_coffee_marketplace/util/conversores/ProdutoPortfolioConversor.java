package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.DetalhesProdutosPortifolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPortfolioConversor implements Conversores<DetalhesProdutosPortifolio, ProdutoPortfolioDto> {

    @Override
    public ProdutoPortfolioDto converte(DetalhesProdutosPortifolio entidade) {
        ProdutoPortfolioDto dto = new ProdutoPortfolioDto();
        dto.setId(entidade.getId());
        dto.setProdutoId(entidade.getProduto().getId());
        dto.setValor(entidade.getValor());
        dto.setQuantidade(entidade.getQuantidadeParaDesconto());
        dto.setDesconto(entidade.getDesconto());
        dto.setTipoDesconto(entidade.getTipoDesconto());

        return dto;
    }

    @Override
    public DetalhesProdutosPortifolio converte(ProdutoPortfolioDto dto) throws ValidationsException {
        DetalhesProdutosPortifolio entidade = new DetalhesProdutosPortifolio();
        entidade.setId(dto.getId());

        Produto produto = new Produto();
        produto.setId(dto.getProdutoId());

        entidade.setProduto(produto);
        entidade.setValor(dto.getValor());
        entidade.setQuantidadeParaDesconto(dto.getQuantidade());
        entidade.setDesconto(dto.getDesconto());
        entidade.setTipoDesconto(dto.getTipoDesconto());

        return entidade;
    }
}
