package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.negocio.ProdutoBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produto")
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
    public ResponseEntity<ProdutoDto> insert(@RequestBody ProdutoDto produto) {
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
