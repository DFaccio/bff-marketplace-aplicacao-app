package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.ProdutoDto;
import br.com.dducl.collective_coffee_marketplace.negocio.ProdutoBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produto")
@Tag(name = "Produto")
public class ProdutoController {

    @Resource
    private ProdutoBusiness business;

    @GetMapping
    public ResponseEntity<ResultadoPaginado<ProdutoDto>> findAll(@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ProdutoDto> findProdutoById(@PathVariable int id) {
        return ResponseEntity.ok(business.findProdutoById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<ProdutoDto> insert(@RequestBody ProdutoDto produto) throws NotFoundException, ValidationsException {
        produto = business.insert(produto);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(produto);
    }

    @PutMapping("/update")
    public ResponseEntity<ProdutoDto> update(@RequestBody ProdutoDto produto) throws ValidationsException {
        produto = business.update(produto);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(produto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProdutoDto> delete(@PathVariable int id) {
        ProdutoDto produto = business.delete(business.findProdutoById(id));

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(produto);
    }
}
