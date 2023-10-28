package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortifolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findPortfolioByIdFornecedor(Integer idFornecedor);

    /** TO DO CONVERSAR COM A EQUIPE, BUSCAR PORTIFÓLIOS DE UM FORNECEDOR COM BASE NO IDENTIFICADOR DO FORNECEDOR**/


}
