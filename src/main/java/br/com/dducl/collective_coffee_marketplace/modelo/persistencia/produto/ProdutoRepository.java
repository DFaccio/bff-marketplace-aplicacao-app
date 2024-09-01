package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.produto;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>, ProdutoRepositoryCustom {

    Page<Produto> findByDisponivelAndFornecedor_PessoaDocumento(boolean status, String documento, Pageable pageable);

    List<Produto> findAllByIdIn(Set<Integer> produtosId);
}
