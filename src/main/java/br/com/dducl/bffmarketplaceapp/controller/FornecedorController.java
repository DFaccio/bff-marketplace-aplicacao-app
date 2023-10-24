package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.FornecedorDto;
import br.com.dducl.bffmarketplaceapp.negocio.FornecedorBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<FornecedorDto> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(business.findById(id));
    }

    @PostMapping("/new")
    public ResponseEntity<FornecedorDto> insert(@RequestBody FornecedorDto fornecedor) throws ValidationsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(business.insert(fornecedor));
    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<FornecedorDto> findByIdentificador(@PathVariable String identificador) throws NotFoundException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
