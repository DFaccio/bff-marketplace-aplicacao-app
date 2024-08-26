package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraDto;
import br.com.dducl.collective_coffee_marketplace.dto.GrupoCompraFullDto;
import br.com.dducl.collective_coffee_marketplace.negocio.GrupoCompraBusiness;
import br.com.dducl.collective_coffee_marketplace.util.Pagination;
import br.com.dducl.collective_coffee_marketplace.util.ResultadoPaginado;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grupo-compra")
@Tag(name = "Grupo de Compra")
public class GrupoCompraController {

    @Resource
    private GrupoCompraBusiness business;

    @PostMapping
    @Operation(description = "Cadastrar grupo de compra")
    public ResponseEntity<GrupoCompraDto> insert(@Valid @RequestBody GrupoCompraFullDto grupoCompra) throws NotFoundException, ValidationsException {
        return ResponseEntity.ok(business.insert(grupoCompra));
    }

    @PutMapping(value = "/id/{id}")
    @Operation(description = "Atualizar grupo de compra. Administrador do grupo não é alterado")
    public ResponseEntity<GrupoCompraFullDto> update(@Valid @RequestBody GrupoCompraFullDto grupoCompra) throws NotFoundException, ValidationsException {
        return ResponseEntity.ok(business.update(grupoCompra));
    }

    @GetMapping
    @Operation(description = "Recuperar os grupos de compra")
    public ResponseEntity<ResultadoPaginado<GrupoCompraFullDto>> findAll(@RequestParam(required = false) Integer pageSize,
                                                                         @RequestParam(required = false) Integer initialPage,
                                                                         @Parameter(description = "Status do grupo. Refere-se a está ou não ativo") @RequestParam(required = false, defaultValue = "true") boolean status) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(business.findAll(status, page));
    }
}
