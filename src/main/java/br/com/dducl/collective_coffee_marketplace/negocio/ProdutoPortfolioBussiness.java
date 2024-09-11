package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.DetalhesProdutosPortifolio;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.ProdutoPortfolioRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.produto.ProdutoRepository;
import br.com.dducl.collective_coffee_marketplace.util.conversores.ProdutoPortfolioConversor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProdutoPortfolioBussiness {

    private final ProdutoRepository produtoRepository;

    private final ProdutoPortfolioRepository produtoPortfolioRepository;

    private final ProdutoPortfolioConversor conversor;

    public ProdutoPortfolioBussiness(ProdutoRepository produtoRepository, ProdutoPortfolioRepository produtoPortfolioRepository, ProdutoPortfolioConversor conversor) {
        this.produtoRepository = produtoRepository;
        this.produtoPortfolioRepository = produtoPortfolioRepository;
        this.conversor = conversor;
    }

    public Set<DetalhesProdutosPortifolio> normalizaAndValidaProdutos(Set<DetalhesProdutosPortifolio> produtoPortfolios) throws ValidationsException {
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
                produtoPortfolio.setProduto(produtosMap.get(produtoPortfolio.getProduto().getId()));

                if (BigDecimal.ZERO.equals(produtoPortfolio.getValor())) {
                    produtoPortfolio.setValor(produtoPortfolio.getProduto().getValor());
                }

                produtos.add(produtoPortfolio);
            }
        });

        return produtos;
    }

    public void atualizaProduto(Set<DetalhesProdutosPortifolio> produtos, Integer produtoId, ProdutoPortfolioDto produtoPortfolio) throws NotFoundException {
        Optional<DetalhesProdutosPortifolio> optionaltoUpdate = findAnyProdutoById(produtos, produtoId);

        if (optionaltoUpdate.isEmpty()) {
            throw new NotFoundException("PRODUTO_ADD_PORTFOLIO_NAO_ENCONTRADO");
        }

        DetalhesProdutosPortifolio toUpdate = optionaltoUpdate.get();
        toUpdate.setValor(produtoPortfolio.getValor());
        toUpdate.setDesconto(produtoPortfolio.getDesconto());
        toUpdate.setTipoDesconto(produtoPortfolio.getTipoDesconto());

        produtoPortfolioRepository.save(toUpdate);
    }

    private static Optional<DetalhesProdutosPortifolio> findAnyProdutoById(Set<DetalhesProdutosPortifolio> produtos, Integer produtoId) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(produtoId))
                .findAny();
    }

    public DetalhesProdutosPortifolio adicionaProduto(Set<DetalhesProdutosPortifolio> portfolio, ProdutoPortfolioDto produtoPortfolio) throws ValidationsException, NotFoundException {
        Optional<DetalhesProdutosPortifolio> optional = findAnyProdutoById(portfolio, produtoPortfolio.getProdutoId());

        if (optional.isPresent()) {
            throw new ValidationsException("PRODUTO_JA_EXISTE_NO_PORTFOLIO");
        }

        DetalhesProdutosPortifolio novo = conversor.converte(produtoPortfolio);

        Optional<Produto> optionalProduto = produtoRepository.findById(novo.getProduto().getId());

        if (optionalProduto.isEmpty()) {
            throw new NotFoundException("PRODUTO_ADD_PORTFOLIO_NAO_ENCONTRADO");
        }

        novo.setProduto(optionalProduto.get());

        if (BigDecimal.ZERO.equals(novo.getValor())) {
            novo.setValor(novo.getProduto().getValor());
        }

        return novo;
    }
}
