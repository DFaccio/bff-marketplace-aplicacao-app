package br.com.dducl.collective_coffee_marketplace.negocio;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoDto;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Fornecedor;
import br.com.dducl.collective_coffee_marketplace.modelo.entidades.Produto;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.fornecedor.FornecedorRepository;
import br.com.dducl.collective_coffee_marketplace.modelo.persistencia.produto.ProdutoRepository;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.conversores.ProdutoConversor;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoBusiness {

    @Resource
    private ProdutoConversor conversor;

    @Resource
    private ProdutoRepository repository;

    @Resource
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private Clock clock;

    public ResultadoPaginado<ProdutoDto> findAll(String documento, boolean status, Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("descricao"));

        Page<Produto> pagina = repository.findByDisponivelAndFornecedor_PessoaDocumento(status, documento, pageable);

        return conversor.converteEntidades(pagina);
    }

    public ProdutoDto findProdutoById(Integer id) throws NotFoundException {
        Optional<Produto> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new NotFoundException("Produto");
        }

        return conversor.converte(optional.get());
    }

    public ProdutoDto update(Integer id, ProdutoDto dto) throws ValidationsException {
        Optional<Produto> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ValidationsException("PRODUTO_ATUALIZAR_NAO_ENCONTRADO");
        }

        Produto toUpdate = optional.get();
        toUpdate.setDescricao(dto.getDescricao());
        toUpdate.setConteudo(dto.getConteudo());
        toUpdate.setQuantidade(dto.getQuantidade());
        toUpdate.setValor(dto.getValor());
        toUpdate.setDisponivel(dto.isDisponivel());

        toUpdate.setAtualizadoEm(LocalDateTime.now(clock));

        toUpdate = repository.save(toUpdate);

        return conversor.converte(toUpdate);
    }

    public ProdutoDto desativa(Integer id) throws ValidationsException {
        Optional<Produto> optional = repository.findById(id);

        if (optional.isEmpty()) {
            throw new ValidationsException("PRODUTO_ATUALIZAR_NAO_ENCONTRADO");
        }

        Produto desativar = optional.get();
        desativar.setDisponivel(false);

        desativar = repository.save(desativar);

        return conversor.converte(desativar);
    }

    public void insert(String documento, List<ProdutoDto> produtosDto) throws ValidationsException {
        Optional<Fornecedor> optionalFornecedor = fornecedorRepository.findByPessoaDocumento(documento);

        if (optionalFornecedor.isEmpty()) {
            throw new ValidationsException("FORNECEDOR_NAO_ENCONTRADO");
        }

        List<Produto> produtos = conversor.converteDto(produtosDto);
        Fornecedor fornecedor = optionalFornecedor.get();

        produtos.forEach(produto -> {
            produto.setDataCriacao(LocalDateTime.now(clock));
            produto.setFornecedor(fornecedor);
        });

        repository.saveAll(produtos);
    }
}
