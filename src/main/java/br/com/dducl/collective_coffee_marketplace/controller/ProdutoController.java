package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoDto;
import br.com.dducl.collective_coffee_marketplace.negocio.ProdutoBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produto")
@Tag(name = "Produto")
public class ProdutoController {

    @Resource
    private ProdutoBusiness business;

    @GetMapping(value = "/fornecedor/{documento}")
    @Operation(description = "Recuperar produtos")
    public ResponseEntity<ResultadoPaginado<ProdutoDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                 @RequestParam(required = false) Integer initialPage,
                                                                 @Parameter(description = "Refere-se ao produto está ativado") @RequestParam boolean status,
                                                                 @Parameter(description = "Documento do fornecedor") @PathVariable String documento) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(documento, status, page));
    }

    @GetMapping(value = "/id/{id}")
    @Operation(description = "Recuperar o produto por identificador")
    public ResponseEntity<ProdutoDto> findProdutoById(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(business.findProdutoById(id));
    }

    @PostMapping("/fornecedor/{documento}")
    @Operation(description = "Cadastrar produto por fornecedor")
    public ResponseEntity<Void> insert(@RequestBody @NotEmpty @Valid List<ProdutoDto> produtos,
                                       @Parameter(description = "Documento do fornecedor") @PathVariable String documento) throws ValidationsException {
        business.insert(documento, produtos);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Atualizar produto. Informações do fornecedor não serão atualizadas")
    public ResponseEntity<ProdutoDto> update(@RequestBody @Valid ProdutoDto produto,
                                             @PathVariable Integer id) throws ValidationsException {
        return ResponseEntity.ok(business.update(id, produto));
    }

    @PutMapping("/desativa/{id}")
    @Operation(description = "Desativar produto")
    public ResponseEntity<ProdutoDto> delete(@PathVariable int id) throws ValidationsException {
        return ResponseEntity.ok(business.desativa(id));
    }
}
