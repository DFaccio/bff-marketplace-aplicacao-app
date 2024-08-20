package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraCadastroUpdateDto;
import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraDto;
import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraFullDto;
import br.com.dducl.collective_coffee_marketplace.negocio.GrupoCompraBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupo-compra")
@Tag(name = "Grupo de Compra", description = "Manipulações com os grupos de compras")
public class GrupoCompraController {

    @Resource
    private GrupoCompraBusiness grupoCompraBusiness;

    @PostMapping
    @Operation(description = "Cadastrar grupo de compra")
    public ResponseEntity<GrupoCompraDto> insert(@Valid @RequestBody GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        return ResponseEntity.ok(grupoCompraBusiness.insert(grupoCompraDto));
    }

    @PutMapping
    @Operation(description = "Atualizar grupo de compra")
    public ResponseEntity<GrupoCompraDto> update(@Valid @RequestBody GrupoCompraCadastroUpdateDto grupoCompraDto) throws NotFoundException {
        return ResponseEntity.ok(grupoCompraBusiness.update(grupoCompraDto));
    }

    @GetMapping
    @Operation(description = "Recuperar os grupos de compra")
    public ResponseEntity<ResultadoPaginado<GrupoCompraFullDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                         @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(grupoCompraBusiness.findAll(page));
    }
}
