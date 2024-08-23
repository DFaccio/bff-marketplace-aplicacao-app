package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.negocio.ChavePixBusiness;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chaves-pix")
@Tag(name = "Chaves PIX")
public class ChavesPixController {

    @Resource
    private ChavePixBusiness chavePixBusiness;

    @PostMapping(value = "/pessoa/{documento}")
    @Operation(description = "Cadastrar novas chaves")
    public ResponseEntity<Void> insert(@Valid @RequestBody List<ChavesPixDto> chavesPixDto,
                                       @Parameter(description = "Documento do cliente") @PathVariable String documento) throws NotFoundException, ValidationsException, NoSuchFieldException {
        chavePixBusiness.insert(documento, chavesPixDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/pessoa/{documento}/chave/{chave}")
    @Operation(description = "Atualizar chave")
    public ResponseEntity<ChavesPixDto> update(@Parameter(description = "Novas informações da chave") @Valid @RequestBody ChavesPixDto chavesPixDto,
                                               @Parameter(description = "Documento da pessoa ao qual a chave pertence") @PathVariable String documento,
                                               @Parameter(description = "Chave a ser atualizada") @RequestParam String chave) throws ValidationsException, NotFoundException {
        return ResponseEntity.ok(chavePixBusiness.update(documento, chave, chavesPixDto));
    }
}
