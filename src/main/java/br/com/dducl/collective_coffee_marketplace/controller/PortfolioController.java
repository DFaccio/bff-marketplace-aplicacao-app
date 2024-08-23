package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.negocio.PortfolioBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/portfolio")
@Tag(name = "Portifólio", description = "Operações com o os itens de venda")
public class PortfolioController {

    @Resource
    private PortfolioBusiness business;

    @GetMapping
    @Operation(description = "Recuperar itens de venda")
    public ResponseEntity<ResultadoPaginado<PortfolioDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                   @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @PostMapping("/new")
    @Operation(description = "Adicionar itens de venda")
    public ResponseEntity<PortfolioDto> insert(@RequestBody PortfolioDto portfolio) throws ValidationsException, NotFoundException {
        portfolio = business.insert(portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(portfolio);
    }

    @PutMapping("/update")
    public ResponseEntity<PortfolioDto> update(@RequestBody PortfolioDto portfolio) throws ValidationsException, NotFoundException {
        portfolio = business.update(portfolio);

        return ResponseEntity.ok(portfolio);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Deletar o portifólio")
    public ResponseEntity<String> delete(@PathVariable int id) throws NotFoundException {
        business.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso!");
    }


    @GetMapping(value = "/id/{id}")
    public ResponseEntity<PortfolioDto> findById(@PathVariable Integer id) throws ValidationsException {
        return ResponseEntity.ok(business.findPortfolioById(id));
    }
}