package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.PessoaDto;
import br.com.dducl.collective_coffee_marketplace.negocio.PessoaBusiness;
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
@RequestMapping(value = "/pessoa")
@Tag(name = "Pessoa")
public class PessoaController {

    @Resource
    private PessoaBusiness business;

    @GetMapping
    public ResponseEntity<ResultadoPaginado<PessoaDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(page));
    }

    @PostMapping("/new")
    public ResponseEntity<PessoaDto> insert(@Valid @RequestBody PessoaDto pessoa) throws ValidationsException {
        PessoaDto dto = business.insert(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<PessoaDto> update(@Valid @RequestBody PessoaDto pessoa) throws ValidationsException {
        PessoaDto dto = business.update(pessoa);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<PessoaDto> findByIdentificador(@PathVariable String identificador) throws NotFoundException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
