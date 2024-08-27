package br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor;

import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

interface FornecedorRepositoryCustom {

    Page<Fornecedor> findAll(String razaoSocial, String telefone, String documento, String email, Pageable pageable);
}
