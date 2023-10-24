package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.negocio.PessoaBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import br.com.dducl.bffmarketplaceapp.util.exceptions.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pessoa")
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
    public ResponseEntity<PessoaDto> insert(@RequestBody PessoaDto pessoa) throws ValidationsException {
        PessoaDto dto = business.insert(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/update")
    public ResponseEntity<PessoaDto> update(@RequestBody PessoaDto pessoa) throws ValidationsException {
        PessoaDto dto = business.update(pessoa);

        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<PessoaDto> findByIdentificador(@PathVariable String identificador) throws NotFoundException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
