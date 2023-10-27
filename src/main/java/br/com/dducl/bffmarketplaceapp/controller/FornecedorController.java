package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.FornecedorDto;
import br.com.dducl.bffmarketplaceapp.modelo.entidades.Fornecedor;
import br.com.dducl.bffmarketplaceapp.negocio.FornecedorBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidacoesException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fornecedor")
public class FornecedorController {

    @Resource
    private FornecedorBusiness business;

    @GetMapping
    public ResponseEntity<ResultadoPaginado<FornecedorDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                    @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FornecedorDto> findById(@PathVariable Integer id) throws ValidacoesException {
        return ResponseEntity.ok(business.findById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<FornecedorDto> insert(@RequestBody FornecedorDto fornecedor) throws ValidacoesException {
        fornecedor = business.insert(fornecedor);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(fornecedor);
    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<FornecedorDto> findByIdentificador(@PathVariable String identificador) throws ValidacoesException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
