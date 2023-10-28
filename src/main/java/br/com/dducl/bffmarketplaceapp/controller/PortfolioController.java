package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.PortfolioDto;
import br.com.dducl.bffmarketplaceapp.negocio.PortfolioBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/portfolio")
public class PortfolioController {

    @Resource
    private PortfolioBusiness business;

    @GetMapping
    public ResponseEntity<ResultadoPaginado<PortfolioDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                   @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @PostMapping("/new")
    public ResponseEntity<PortfolioDto> insert(@RequestBody PortfolioDto portfolio) throws ValidacoesException {
        portfolio = business.insert(portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(portfolio);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<PortfolioDto> findById(@PathVariable Integer id) throws ValidacoesException {
        return ResponseEntity.ok(business.findPortfolioById(id));
    }
}
