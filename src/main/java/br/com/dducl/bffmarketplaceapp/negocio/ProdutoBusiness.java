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

    public ProdutoDto findProdutoById(int descricao) {
        var produto = (repository.getReferenceById(descricao));
        return conversor.converte(produto);
    }

    public void update(ProdutoDto dto) throws ValidacoesException {
        var produto = (repository.getReferenceById(dto.getId()));

        if (produto.equals(null)) {
            throw new ValidacoesException("Produto informado para atualiza\u00E7\u00E3o n\u00E3o foi encontrado!");
        }

        Produto atualizar = produto;
        if (dto.getConteudo() != null) {
            atualizar.setConteudo(dto.getConteudo());
        }
        if (dto.getDescricao() != null) {
            atualizar.setDescricao(dto.getDescricao());
        }
        if (dto.getQuantidade() != 0) {
            atualizar.setQuantidade(dto.getQuantidade());
        }
        if (dto.getValor() != null) {
            atualizar.setValor(dto.getValor());
        }

        repository.save(atualizar);
    }

    public void delete(ProdutoDto dto){
        var produto = (repository.getReferenceById(dto.getId()));
        Produto atualizar = produto;

        atualizar.setDisponivel(false);
        repository.save(atualizar);
    }
}
