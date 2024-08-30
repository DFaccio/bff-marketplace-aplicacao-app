package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioCadastroDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio.PortfolioResumidoDto;
import br.com.dducl.collective_coffee_marketplace.dto.portfolio_produto.ProdutoPortfolioDto;
import br.com.dducl.collective_coffee_marketplace.negocio.PortfolioBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.enums.StatusPortfolio;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/portfolio")
@Tag(name = "Portfólio", description = "Gerenciamento dos itens a serem apresentados ao grupo de compra")
public class PortfolioController {

    @Resource
    private PortfolioBusiness business;

    @GetMapping
    @Operation(description = "Recuperar portfólios. Sempre consulta um período de D+30. Quando data de encerramento é informada, esta data passa a ser o parâmetro")
    public ResponseEntity<ResultadoPaginado<PortfolioDto>> findAll(@Parameter(description = "Máximo = 1000") @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                   @RequestParam(required = false, defaultValue = "0") Integer initialPage,
                                                                   @Parameter(description = "Documento do fornecedor") @RequestParam(required = false) String documento,
                                                                   @Parameter(description = "Identificador do fornecedor") @RequestParam(required = false) Integer idFornecedor,
                                                                   @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}") @Parameter(description = "Data de finalização") @RequestParam(required = false) String dataEncerramento,
                                                                   @RequestParam StatusPortfolio status,
                                                                   @RequestParam(required = false) @Parameter(description = "Nome do produto") String produto) throws ValidationsException {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(documento, idFornecedor, dataEncerramento, status, produto, page));
    }

    @PostMapping
    @Operation(description = "Adicionar itens de venda")
    public ResponseEntity<PortfolioResumidoDto> insert(@RequestBody PortfolioCadastroDto portfolio) throws ValidationsException, NotFoundException {
        portfolio = business.insert(portfolio);

        return ResponseEntity.status(HttpStatus.CREATED).body(portfolio);
    }

    @PutMapping
    public ResponseEntity<PortfolioResumidoDto> update(@RequestBody PortfolioResumidoDto portfolio) throws ValidationsException, NotFoundException {
        portfolio = business.update(portfolio);

        return ResponseEntity.ok(portfolio);
    }

    @PutMapping(value = "/id/{id}")
    @Operation(description = "Atualizar as inforamações de um item. O objeto produto não é alterado")
    public ResponseEntity<PortfolioDto> update(@RequestBody ProdutoPortfolioDto produtoPortfolio, @PathVariable Integer id) {
        return ResponseEntity.ok(business.update(id, produtoPortfolio));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) throws NotFoundException {
        business.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<PortfolioResumidoDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(business.findPortfolioById(id));
    }
}