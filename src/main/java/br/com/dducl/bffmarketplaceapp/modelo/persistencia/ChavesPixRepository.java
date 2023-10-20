package br.com.dducl.bffmarketplaceapp.modelo.persistencia;

import br.com.dducl.bffmarketplaceapp.modelo.entidades.ChavesPix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChavesPixRepository extends JpaRepository<ChavesPix, String> {

}
