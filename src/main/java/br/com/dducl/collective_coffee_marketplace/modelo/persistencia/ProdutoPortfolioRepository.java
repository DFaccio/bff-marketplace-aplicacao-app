package br.com.dducl.collective_coffee_marketplace.modelo.persistencia;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.DetalhesProdutosPortifolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoPortfolioRepository extends JpaRepository<DetalhesProdutosPortifolio, Integer> {
}
