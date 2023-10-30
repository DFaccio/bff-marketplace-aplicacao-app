package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.PessoaDto;
import br.com.dducl.bffmarketplaceapp.negocio.PessoaBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatusCode;
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
        pessoa = business.insert(pessoa);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(pessoa);
    }

    @PutMapping("/update")
    public ResponseEntity<PessoaDto> update(@RequestBody PessoaDto pessoa) throws ValidationsException {
        pessoa = business.update(pessoa);

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(pessoa);
    }

    @GetMapping(value = "/identificador/{identificador}")
    public ResponseEntity<PessoaDto> findByIdentificador(@PathVariable String identificador) throws ValidationsException {
        return ResponseEntity.ok(business.findByIdentificador(identificador));
    }
}
