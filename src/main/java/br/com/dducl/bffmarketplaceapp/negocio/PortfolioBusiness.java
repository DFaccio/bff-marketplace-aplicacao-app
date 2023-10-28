package br.com.dducl.bffmarketplaceapp.negocio;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Portfolio;
import br.com.dducl.bffmarketplaceapp.modelo.persistencia.PortfolioRepository;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import br.com.dducl.bffmarketplaceapp.util.conversores.PortfolioConversor;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PortfolioBusiness {

    @Resource
    private PortfolioConversor conversor;

    @Resource
    private PortfolioRepository repository;

    public ResultadoPaginado<PortfolioDto> findAll(Pagination page) {
        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize(), Sort.by("descricao"));

        Page<Portfolio> pagina = repository.findAll(pageable);

        return conversor.converteEntidades(pagina);
    }

    public PortfolioDto insert(PortfolioDto dto) throws ValidacoesException {

        Portfolio portfolio = conversor.converte(dto);

        /* Optional<Portfolio> portifolioCriado = repository.findPortfolioByIdEquals(portfolio.getId());

        if (portifolioCriado.isPresent()) {
            throw new ValidacoesException("Portfólio ja cadastrado com essa descricao verifique!");
        }
        */

        dto = conversor.converte(repository.save(portfolio));
        return dto;
    }

    public PortfolioDto findPortfolioById(int id) {
        var portfolio = (repository.getReferenceById(id));
        return conversor.converte(portfolio);
    }



}
