package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>, FornecedorRepositoryCustom {

    Optional<Fornecedor> findByPessoaDocumento(String identificador);
}
