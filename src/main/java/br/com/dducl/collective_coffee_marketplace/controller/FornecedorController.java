package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.FornecedorDto;
import br.com.dducl.collective_coffee_marketplace.negocio.FornecedorBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fornecedor")
@Tag(name = "Fornecedor")
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

    public ResponseEntity<FornecedorDto> insert(@Valid @RequestBody FornecedorDto fornecedor) throws ValidationsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(business.insert(fornecedor));

    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<FornecedorDto> findByIdentificador(@PathVariable String identificador) throws NotFoundException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
