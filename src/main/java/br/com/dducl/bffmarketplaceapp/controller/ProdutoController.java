package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.dto.ProdutoDto;
import br.com.dducl.bffmarketplaceapp.negocio.ProdutoBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {

    @Resource
    private ProdutoBusiness business;

    @GetMapping
    public ResponseEntity<ResultadoPaginado<ProdutoDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ProdutoDto> findProdutoById(@PathVariable int id) throws ValidacoesException {
        return ResponseEntity.ok(business.findProdutoById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Void> insert(@RequestBody ProdutoDto produto) throws ValidacoesException {
        business.insert(produto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody ProdutoDto produto) throws ValidacoesException {
        business.update(produto);

        return ResponseEntity.noContent().build();
    }

}
