package br.com.dducl.collective_coffee_marketplace.util.conversores;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto.ProdutoPortfolioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.ProdutoPortfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProdutoPortfolioConversor implements Conversores<ProdutoPortfolio, ProdutoPortfolioDto> {

    private final ProdutoConversor produtoConversor;

    public ProdutoPortfolioConversor(ProdutoConversor produtoConversor) {
        this.produtoConversor = produtoConversor;
    }

    @Override
    public ProdutoPortfolioDto converte(ProdutoPortfolio entidade) {
        ProdutoPortfolioDto dto = new ProdutoPortfolioDto();
        dto.setId(entidade.getId());
        dto.setProduto(produtoConversor.converte(entidade.getProduto()));
        dto.setValor(entidade.getValor());
        dto.setQuantidade(entidade.getQuantidadeParaDesconto());
        dto.setDesconto(entidade.getDesconto());
        dto.setTipoDesconto(entidade.getTipoDesconto());

        return dto;
    }

    @Override
    public ProdutoPortfolio converte(ProdutoPortfolioDto dto) throws ValidationsException {
        ProdutoPortfolio entidade = new ProdutoPortfolio();
        entidade.setId(dto.getId());
        entidade.setProduto(produtoConversor.converte(dto.getProduto()));
        entidade.setValor(dto.getValor());
        entidade.setQuantidadeParaDesconto(dto.getQuantidade());
        entidade.setDesconto(dto.getDesconto());
        entidade.setTipoDesconto(dto.getTipoDesconto());

        return entidade;
    }

    public Set<ProdutoPortfolio> converte(List<ProdutoPortfolioCadastroDto> produtos) {
        return produtos.stream()
                .map(dto -> {
                    Produto produto = new Produto();
                    produto.setId(dto.produtoId());

                    ProdutoPortfolio produtoPortfolio = new ProdutoPortfolio();
                    produtoPortfolio.setProduto(produto);
                    produtoPortfolio.setValor(dto.valor());
                    produtoPortfolio.setQuantidadeParaDesconto(dto.quantidade());
                    produtoPortfolio.setDesconto(dto.desconto());
                    produtoPortfolio.setTipoDesconto(dto.tipoDesconto());

                    return produtoPortfolio;
                })
                .collect(Collectors.toCollection(HashSet::new));
    }
}
