package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Produto;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.ProdutoRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.ProdutoConversor;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public ResultadoPaginado<ProdutoDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("descricao"));

        Page<Produto> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public ProdutoDto findProdutoById(int descricao) throws ValidacoesException {
        var produto = (repository.getReferenceById(descricao));
        return conversor.converte(produto);
    }

}
