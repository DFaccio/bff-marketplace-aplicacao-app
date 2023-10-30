package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.PortfolioProdutos;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.FornecedorRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PortfolioProdutosRepository;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PortfolioRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.FornecedorConversor;
import br.com.dducl.bffmarketplaceapp.util.conversores.PortfolioConversor;
import br.com.dducl.bffmarketplaceapp.util.conversores.ProdutoConversor;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PortfolioBusiness {

    @Resource
    private PortfolioConversor conversor;

    @Resource
    private PortfolioRepository repository;

    @Resource
    private PortfolioProdutosRepository portfolioProdutosRepository;

    @Resource
    private FornecedorRepository fornecedorRepository;

    @Resource
    private FornecedorConversor fornecedorConversor;

    @Resource
    private ProdutoConversor produtoConversor;

    public ResultadoPaginado<PortfolioDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("descricao"));

        Page<Portfolio> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public PortfolioDto insert(PortfolioDto dto) throws ValidacoesException {
        Portfolio portfolio = conversor.converte(dto);

        Optional<Fornecedor> fornecedor = fornecedorRepository.findFornecedorByPessoaIdentificador(dto.getFornecedor().getInformacoes().getIdentificador());

        if (fornecedor.isPresent()){
            portfolio.setFornecedor(fornecedor.get());
        } else {
            throw new ValidacoesException("Código do fornecedor não encontrado, não foi realizada a inclusão do portfólio.");
        }

        Optional<Portfolio> portfolioCriado = repository.findPortfolioByFornecedorAndDescricao(portfolio.getFornecedor(), portfolio.getDescricao());

        if (portfolioCriado.isPresent()) {
            throw new ValidacoesException("Portfólio já cadastrado para este fornecedor e mesma descrição, por favor verifique!");
        }

        portfolio.setDataCriacao(LocalDateTime.now());

        PortfolioDto dtoListaProdutos = dto;

        Portfolio portfolioSalvo = repository.save(portfolio);
        dto = conversor.converte(portfolioSalvo);
        dto.setFornecedor( fornecedorConversor.converte(portfolio.getFornecedor()));

        List<PortfolioProdutos> portfolioProdutos = dtoListaProdutos.getPortfolioProdutos().stream().map(produto -> {
            PortfolioProdutos novoPortfolioProduto = new PortfolioProdutos();

            novoPortfolioProduto.setPortfolio(portfolioSalvo);
            novoPortfolioProduto.setProduto(produtoConversor.converte(produto));
            novoPortfolioProduto.setDataCriacao(LocalDateTime.now());
            return novoPortfolioProduto;
        }).toList();

        portfolioProdutosRepository.saveAll(portfolioProdutos);

        return dto;
    }

    public PortfolioDto findPortfolioById(int id) {
        var portfolio = (repository.getReferenceById(id));
        return conversor.converte(portfolio);
    }

    public PortfolioDto update(PortfolioDto dto) throws ValidacoesException {
        Optional<Portfolio> portfolio = repository.findPortfolioById(dto.getId());

        if (portfolio.isEmpty()) {
            throw new ValidacoesException("ID do portfolio informado não é válido, não é possível alterar!");
        }

        Portfolio portfolioToUpdate = portfolio.get();
        portfolioToUpdate.setStatus(dto.getStatus());
        portfolioToUpdate.setDescricao(dto.getDescricao());
        portfolioToUpdate.setDataVigencia(dto.getDataVigencia());

        return conversor.converte(repository.save(portfolioToUpdate));
    }

    public String delete(Integer IdPortifolio){
        Optional<Portfolio> portfolioDeletar = repository.findPortfolioById(IdPortifolio);


        if (portfolioDeletar.isEmpty()){
            return "Código do portifólio não encontrado para excluir!";
        }

        Iterable<PortfolioProdutos> portfolioRepository = portfolioProdutosRepository.findByPortfolioEquals(repository.getReferenceById(IdPortifolio));
        portfolioProdutosRepository.deleteAll(portfolioRepository);

        repository.deleteById(IdPortifolio);

        return "Portfólio deletado com sucesso!";
    }
}
