package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.GrupoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoCompraRepository extends JpaRepository<GrupoCompra, Integer> {

}
