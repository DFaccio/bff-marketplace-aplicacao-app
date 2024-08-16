package br.com.dducl.collective_coffee_marketplace.controller;

import br.com.dducl.collective_coffee_marketplace.dto.ChavesPixDto;
import br.com.dducl.collective_coffee_marketplace.negocio.ChavePixBusiness;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.NotFoundException;
import br.com.dducl.collective_coffee_marketplace.util.exceptions.ValidationsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chaves-pix")
@Tag(name = "Chaves PIX")
public class ChavesPixController {

    @Resource
    private ChavePixBusiness chavePixBusiness;

    @PostMapping(value = "/new")
    public ResponseEntity<Void> insert(@Valid @RequestBody ChavesPixDto chavesPixDto, @RequestParam String identificador) throws NotFoundException, ValidationsException, NoSuchFieldException {
        chavePixBusiness.insert(identificador, chavesPixDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/pessoa/{identificador}")
    public ResponseEntity<Void> update(@Valid @RequestBody ChavesPixDto chavesPixDto, @PathVariable String identificador, @RequestParam String chave) throws ValidationsException {
        chavePixBusiness.update(chave, chavesPixDto, identificador);

        return ResponseEntity.noContent().build();
    }
}
