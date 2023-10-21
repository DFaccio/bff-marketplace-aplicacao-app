package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Produto;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.ProdutoRepository;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.ProdutoConversor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProdutoBusiness {

    @Resource
    private ProdutoConversor conversor;

    @Resource
    private ProdutoRepository repository;

    public void insert(ProdutoDto dto) throws ValidacoesException {
        Produto produto = conversor.converte(dto);

        produto.setDataCriacao(LocalDateTime.now());

        repository.save(produto);
    }
}
