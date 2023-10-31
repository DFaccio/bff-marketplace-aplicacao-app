package br.com.dducl.bffmarketplaceapp.controller;

import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraCadastroUpdateDto;
import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraDto;
import br.com.dducl.bffmarketplaceapp.dto.GrupoCompraFullDto;
import br.com.dducl.bffmarketplaceapp.negocio.GrupoCompraBusiness;
import br.com.dducl.bffmarketplaceapp.util.Pagination;
import br.com.dducl.bffmarketplaceapp.util.ResultadoPaginado;
import br.com.dducl.bffmarketplaceapp.util.exceptions.NotFoundException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupo-compra")
public class GrupoCompraController {

    @Resource
    private GrupoCompraBusiness grupoCompraBusiness;

    @PostMapping
    public ResponseEntity<GrupoCompraDto> insert(@Valid @RequestBody GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        return ResponseEntity.ok(grupoCompraBusiness.insert(grupoCompraDto));
    }

    @PutMapping
    public ResponseEntity<GrupoCompraDto> update(@Valid @RequestBody GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        return ResponseEntity.ok(grupoCompraBusiness.update(grupoCompraDto));
    }

    @GetMapping
    public ResponseEntity<ResultadoPaginado<GrupoCompraFullDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                         @RequestParam(required = false) Integer initialPage) {

        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(grupoCompraBusiness.findAll(page));
    }
}
